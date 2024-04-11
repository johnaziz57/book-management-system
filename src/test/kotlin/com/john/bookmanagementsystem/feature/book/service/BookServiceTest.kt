package com.john.bookmanagementsystem.feature.book.service

import com.john.bookmanagementsystem.feature.author.repository.AuthorRepository
import com.john.bookmanagementsystem.feature.book.model.Book
import com.john.bookmanagementsystem.feature.book.model.BookStatistics
import com.john.bookmanagementsystem.feature.book.repository.BookRepository
import com.john.bookmanagementsystem.feature.book.repository.BookStatisticsRepository
import com.john.bookmanagementsystem.feature.book.repository.BorrowLogRepository
import com.john.bookmanagementsystem.feature.user.model.Role
import com.john.bookmanagementsystem.feature.user.model.User
import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
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

    private lateinit var subject: BookService

    @BeforeEach
    fun setup() {
        subject = BookService(
            bookRepository = bookRepository,
            authorRepository = authorRepository,
            borrowLogRepository = borrowLogRepository,
            bookStatisticsRepository = bookStatisticsRepository,
            userRepository = userRepository
        )
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
}