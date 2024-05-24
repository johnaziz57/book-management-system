package com.john.bookmanagementsystem.feature.book.service

import com.john.bookmanagementsystem.commons.ServiceResponseException
import com.john.bookmanagementsystem.feature.author.repository.AuthorRepository
import com.john.bookmanagementsystem.feature.book.model.Book
import com.john.bookmanagementsystem.feature.book.model.BookStatistics
import com.john.bookmanagementsystem.feature.book.model.BorrowLog
import com.john.bookmanagementsystem.feature.book.repository.BookRepository
import com.john.bookmanagementsystem.feature.book.repository.BookStatisticsRepository
import com.john.bookmanagementsystem.feature.book.repository.BorrowLogRepository
import com.john.bookmanagementsystem.feature.user.model.Role
import com.john.bookmanagementsystem.feature.user.model.User
import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@SpringBootTest
@ExtendWith(SpringExtension::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookServiceTest {
    @Mock
    private lateinit var bookRepository: BookRepository

    @Mock
    private lateinit var authorRepository: AuthorRepository

    @Mock
    private lateinit var borrowLogRepository: BorrowLogRepository

    @Mock
    private lateinit var bookStatisticsRepository: BookStatisticsRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var clock: Clock

    private lateinit var subject: BookService

    @BeforeEach
    fun setup() {
        subject = BookService(
            bookRepository = bookRepository,
            authorRepository = authorRepository,
            borrowLogRepository = borrowLogRepository,
            bookStatisticsRepository = bookStatisticsRepository,
            userRepository = userRepository,
            clock = clock
        )
        `when`(clock.zone).thenReturn(ZoneId.of("UTC"))
        `when`(clock.instant()).thenReturn(Instant.parse("2018-08-19T20:00:00.00Z"))
    }

    @Test
    fun `test borrowing an available book`() {
        val book = Book(
            id = 1,
            title = "Testing test",
            ISBN = "ISBN",
            authors = emptySet(),
            availableCopies = 1
        )
        `when`(bookRepository.findById(1)).thenReturn(Optional.of(book))

        val username = "test"
        val user = User(
            id = 1,
            role = Role.ADMIN,
            name = "Test",
            userName = username,
            password = "",
            borrowedBooksCount = 0
        )
        `when`(userRepository.findByUserName(username)).thenReturn(user)

        val bookStatistics = BookStatistics(
            id = 1,
            book = book,
            borrowCount = 0,
            averageBorrowTime = 0
        )
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(bookStatistics)

        subject.borrowBook(1, username)

        verify(bookStatisticsRepository).save(bookStatistics.copy(borrowCount = 1))
        verify(userRepository).save(user.copy(borrowedBooksCount = 1))
        verify(bookRepository).save(book.copy(availableCopies = 0))
    }

    @Test
    fun `test borrowing an unavailable book`() {
        val book = Book(
            id = 1,
            title = "Testing test",
            ISBN = "ISBN",
            authors = emptySet(),
            availableCopies = 0
        )
        `when`(bookRepository.findById(1)).thenReturn(Optional.of(book))

        val username = "test"
        val user = User(
            id = 1,
            role = Role.ADMIN,
            name = "Test",
            userName = username,
            password = "",
            borrowedBooksCount = 0
        )
        `when`(userRepository.findByUserName(username)).thenReturn(user)

        val bookStatistics = BookStatistics(
            id = 1,
            book = book,
            borrowCount = 0,
            averageBorrowTime = 0
        )
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(bookStatistics)

        assertThrows<ServiceResponseException> { subject.borrowBook(1, username) }
    }

    @Test
    fun `test borrowing a book, returning, and borrowing it again`() {
        val book = Book(
            id = 1,
            title = "Testing test",
            ISBN = "ISBN",
            authors = emptySet(),
            availableCopies = 1
        )
        `when`(bookRepository.findById(1)).thenReturn(Optional.of(book))

        val username = "test"
        val user = User(
            id = 1,
            role = Role.ADMIN,
            name = "Test",
            userName = username,
            password = "",
            borrowedBooksCount = 0
        )
        `when`(userRepository.findByUserName(username)).thenReturn(user)

        val bookStatistics = BookStatistics(
            id = 1,
            book = book,
            borrowCount = 0,
            averageBorrowTime = 0
        )
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(bookStatistics)
        val borrowLog = BorrowLog(
            book = book,
            user = user,
            borrowedDate = LocalDateTime.parse("2018-08-19T20:00:00"),
            returnedDate = null
        )

        subject.borrowBook(1, username)

        `when`(borrowLogRepository.findFirstUnreturnedBook(anyLong(), anyLong())).thenReturn(borrowLog)
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(bookStatistics.copy(borrowCount = 1))

        subject.returnBook(1, username)
        subject.borrowBook(1, username)

        verify(bookStatisticsRepository).save(bookStatistics.copy(borrowCount = 2))

    }

    @Test
    fun `test borrowing a book, returning after a while`() {
        val book = Book(
            id = 1,
            title = "Testing test",
            ISBN = "ISBN",
            authors = emptySet(),
            availableCopies = 1
        )
        `when`(bookRepository.findById(1)).thenReturn(Optional.of(book))

        val username = "test"
        val user = User(
            id = 1,
            role = Role.ADMIN,
            name = "Test",
            userName = username,
            password = "",
            borrowedBooksCount = 0
        )
        `when`(userRepository.findByUserName(username)).thenReturn(user)

        val bookStatistics = BookStatistics(
            id = 1,
            book = book,
            borrowCount = 0,
            averageBorrowTime = 0
        )
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(bookStatistics)
        `when`(clock.instant()).thenReturn(Instant.parse("2018-08-19T20:00:00.00Z"))

        subject.borrowBook(1, username)

        `when`(clock.instant()).thenReturn(Instant.parse("2018-08-19T20:00:10.00Z"))
        val borrowLog = BorrowLog(
            book = book,
            user = user,
            borrowedDate = LocalDateTime.parse("2018-08-19T20:00:00"),
            returnedDate = null
        )
        `when`(borrowLogRepository.findFirstUnreturnedBook(anyLong(), anyLong())).thenReturn(borrowLog)
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(bookStatistics.copy(borrowCount = 1))
        subject.returnBook(1, username)


        verify(bookStatisticsRepository).save(bookStatistics.copy(borrowCount = 1, averageBorrowTime = 10))
    }

    @Test
    fun `test borrowing a book twice and average time calculation`() {
        val book = Book(
            id = 1,
            title = "Testing test",
            ISBN = "ISBN",
            authors = emptySet(),
            availableCopies = 1
        )
        `when`(bookRepository.findById(1)).thenReturn(Optional.of(book))

        val username = "test"
        val user = User(
            id = 1,
            role = Role.ADMIN,
            name = "Test",
            userName = username,
            password = "",
            borrowedBooksCount = 0
        )
        `when`(userRepository.findByUserName(username)).thenReturn(user)

        val bookStatistics = BookStatistics(
            id = 1,
            book = book,
            borrowCount = 0,
            averageBorrowTime = 0
        )
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(bookStatistics)
        // current clock is 20:00:00
        `when`(clock.instant()).thenReturn(Instant.parse("2018-08-19T20:00:00.00Z"))

        subject.borrowBook(1, username)

        // Forward clock to 20:00:10
        `when`(clock.instant()).thenReturn(Instant.parse("2018-08-19T20:00:10.00Z"))
        // Update borrow log
        val borrowLog = BorrowLog(
            book = book,
            user = user,
            borrowedDate = LocalDateTime.parse("2018-08-19T20:00:00"),
            returnedDate = null
        )
        `when`(borrowLogRepository.findFirstUnreturnedBook(anyLong(), anyLong())).thenReturn(borrowLog)
        // update statistics
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(
            bookStatistics.copy(
                borrowCount = 1,
                averageBorrowTime = 10
            )
        )

        // Average time now should be 10, borrow count is 1
        subject.returnBook(1, username)

        // borrow count now is 2
        subject.borrowBook(1, username)

        // Forward clock to 20:00:30
        `when`(clock.instant()).thenReturn(Instant.parse("2018-08-19T20:00:30.00Z"))
        // Update borrow log
        val borrowLog2 = BorrowLog(
            book = book,
            user = user,
            borrowedDate = LocalDateTime.parse("2018-08-19T20:00:10"),
            returnedDate = null
        )
        `when`(borrowLogRepository.findFirstUnreturnedBook(anyLong(), anyLong())).thenReturn(borrowLog2)
        // Update statistics
        `when`(bookStatisticsRepository.findByBook(book)).thenReturn(
            bookStatistics.copy(
                borrowCount = 2,
                averageBorrowTime = 10
            )
        )

        subject.returnBook(1, username)

        verify(bookStatisticsRepository).save(bookStatistics.copy(borrowCount = 2, averageBorrowTime = 15))
    }
}