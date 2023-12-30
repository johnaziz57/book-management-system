package com.john.bookmanagementsystem.feature.book.repository

import com.john.bookmanagementsystem.feature.author.model.Author
import com.john.bookmanagementsystem.feature.author.repository.AuthorRepository
import com.john.bookmanagementsystem.feature.book.model.Book
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.TransactionSystemException

@SpringBootTest
@ExtendWith(SpringExtension::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookIntegrationTest(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val authorRepository: AuthorRepository
) {

    @Test
    fun `test book can be saved and recalled`() {
        assert(bookRepository.count() == 0L)
        val book = Book(
            title = "Test book",
            ISBN = "9781234567897"
        )
        bookRepository.save(book)
        assert(bookRepository.count() == 1L)
    }

    @Test
    fun `test book ISBN has to be valid`() {
        assert(bookRepository.count() == 0L)
        val book = Book(
            title = "Test book",
            ISBN = "1"
        )
        assertThrows<TransactionSystemException> {
            bookRepository.save(book)
        }
    }

    @Transactional
    @Test
    fun `test book with author can be saved and recalled`() {
        assert(bookRepository.count() == 0L)
        assert(authorRepository.count() == 0L)

        val authorName = "J.K Rowling"
        val author = Author(
            name = authorName
        )
        val book = Book(
            title = "Harry something",
            ISBN = "9781234567897",
            authors = setOf(author)
        )
        bookRepository.saveAndFlush(book)
        assert(bookRepository.count() == 1L)
        assert(authorRepository.count() == 1L)
        println("TOLORIKO" + authorRepository.findAll().first().name)
//        assert(authorRepository.findAll().first().name == authorName)
    }

    @Transactional
    @Test
    fun `test book when deleting a book, the author is not deleted`() {
        val author = Author(
            name = "J.K Rowling"
        )
        bookRepository.save(
            Book(
                title = "Harry something",
                ISBN = "9781234567897",
                authors = setOf(author)
            )
        )
        assert(bookRepository.count() == 1L)
        assert(authorRepository.count() == 1L)
        val book = bookRepository.findAll().first()
        bookRepository.deleteById(book.id!!)
        assert(authorRepository.count() == 1L)
        assert(bookRepository.count() == 0L)
    }

}
