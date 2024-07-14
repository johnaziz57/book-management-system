package com.john.bookmanagementsystem.feature.book.service

import com.john.bookmanagementsystem.commons.ServiceResponseException
import com.john.bookmanagementsystem.feature.author.repository.AuthorRepository
import com.john.bookmanagementsystem.feature.book.dto.BookDTO
import com.john.bookmanagementsystem.feature.book.model.*
import com.john.bookmanagementsystem.feature.book.repository.BookRepository
import com.john.bookmanagementsystem.feature.book.repository.BookStatisticsRepository
import com.john.bookmanagementsystem.feature.book.repository.BorrowLogRepository
import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDateTime

@Service
class BookService(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val authorRepository: AuthorRepository,
    @Autowired private val borrowLogRepository: BorrowLogRepository,
    @Autowired private val bookStatisticsRepository: BookStatisticsRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val clock: Clock
) {

    fun findAll(pageRequest: PageRequest): List<BookDTO> {
        return bookRepository.findAll(pageRequest).content.map { it.toDTO() }
    }

    fun findById(id: Long): Book {
        bookRepository.findById(id).let {
            if (it.isPresent) {
                return it.get()
            } else {
                throw ServiceResponseException("Book not found", HttpStatus.NOT_FOUND)
            }
        }
    }

    @Transactional
    fun createBook(bookDTO: BookDTO): BookDTO {
        if (findByISBN(bookDTO.ISBN) != null) {
            throw ServiceResponseException("Book ISBN already exits", HttpStatus.INTERNAL_SERVER_ERROR)
        }
        val authors = bookDTO.authors.map { author ->
            authorRepository.findByName(author.name) ?: author.toEntity()
        }
        val bookEntity = bookDTO.toEntity().copy(authors = authors.toSet())
        return bookRepository.save(bookEntity).let {

            bookStatisticsRepository.save(
                BookStatistics(
                    book = bookEntity
                )
            )

            it.toDTO()
        }
    }

    @Transactional
    fun borrowBook(bookId: Long, username: String): BorrowBookResponse {
        val book = bookRepository.findById(bookId)
            .orElseThrow { throw IllegalArgumentException("A book with id:$bookId can't be found") }

        // TODO is there a better way to get the user?
        val user = userRepository.findByUserName(username)
            ?: throw IllegalStateException("A user with username: $username, is trying to borrow a book, but user can't be found")

        if (book.availableCopies < 1) {
            return BorrowBookResponse.Failure("Trying to borrow book with id:${book.id}, but there are no available copies.")
        }

        if (user.borrowedBooksCount >= MAXIMUM_ALLOWED_BORROWED_BOOKS) {
            return BorrowBookResponse.Failure("A user with username: $username, is ")
        }

        val statistics = findBookStatistics(book)

        borrowLogRepository.save(
            BorrowLog(
                user = user,
                book = book,
                borrowedDate = LocalDateTime.now(clock),
                returnedDate = null
            )
        )
        bookStatisticsRepository.save(statistics.recalculateAfterBorrow())
        userRepository.save(user.copy(borrowedBooksCount = user.borrowedBooksCount + 1))
        bookRepository.save(book.copy(availableCopies = book.availableCopies - 1))

        return BorrowBookResponse.Success
    }

    @Transactional
    fun returnBook(bookId: Long, username: String): Boolean {
        val book = bookRepository.findById(bookId)
            .orElseThrow { throw ServiceResponseException("Book doesn't exit", HttpStatus.BAD_REQUEST) }
        val user = userRepository.findByUserName(username)
            ?: throw ServiceResponseException("user is not found", HttpStatus.UNAUTHORIZED)

        user.id ?: throw ServiceResponseException("user is not saved", HttpStatus.UNAUTHORIZED)

        val borrowLog = borrowLogRepository.findFirstUnreturnedBook(bookId, user.id) ?: throw ServiceResponseException(
            "No related book was borrowed",
            HttpStatus.UNAUTHORIZED
        )
        val statistics = findBookStatistics(book)
        val returnedDate = LocalDateTime.now(clock)


        bookStatisticsRepository.save(statistics.recalculateAfterReturn(borrowLog.borrowedDate))
        borrowLogRepository.save(borrowLog.copy(returnedDate = returnedDate))
        userRepository.save(user.copy(borrowedBooksCount = user.borrowedBooksCount - 1))
        bookRepository.save(book.copy(availableCopies = book.availableCopies + 1))
        return true
    }

    fun removeBook(id: Long) {
        bookRepository.deleteById(id)
    }

    fun updateBook(id: Long, bookDTO: BookDTO) {
        bookRepository.findById(id).ifPresentOrElse({
            bookRepository.save(bookDTO.toEntity())
        }, {
            throw ServiceResponseException("Book not found", HttpStatus.NOT_FOUND)
        })
    }

    fun deleteBook(id: Long) {
        bookRepository.deleteById(id)
    }

    fun findByTitle(title: String): List<BookDTO> {
        return bookRepository.findByTitleContainingIgnoreCase(title).map { it.toDTO() }
    }

    /**
     * returns the most borrowed books by rank
     *
     * P.S. This function can be optimized if we kept track of how many times each book was borrowed
     * instead of counting
     */
    fun getMostBorrowed(rankLimit: Int): List<BookDTO> {
        return bookStatisticsRepository.findMostBorrowed(rankLimit)
            .map { it.book.toDTO() }
    }

    fun getLongestBorrowed(rankLimit: Int): List<BookDTO> {
        return bookStatisticsRepository.findLongestBorrowed(rankLimit)
            .map { it.book.toDTO() }
    }

    fun getNotReturned(): List<BookDTO> {
        return borrowLogRepository.findNotReturned()
            .let { bookRepository.findAllById(it) }
            .map { it.toDTO() }
    }

    private fun findByISBN(isbn: String): Book? {
        return bookRepository.findByISBN(isbn)
    }

    private fun findBookStatistics(book: Book): BookStatistics {
        return bookStatisticsRepository.findByBook(book)
            ?: bookStatisticsRepository.saveAndFlush(
                BookStatistics(
                    book = book
                )
            )
    }

    companion object {
        private const val MAXIMUM_ALLOWED_BORROWED_BOOKS = 1
    }
}
