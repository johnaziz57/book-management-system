package com.john.bookmanagementsystem.feature.book.repository

import com.john.bookmanagementsystem.feature.book.model.Book
import com.john.bookmanagementsystem.feature.book.model.BookStatistics
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookStatisticsRepository : JpaRepository<BookStatistics, Book> {

    fun findByBook(book: Book): BookStatistics?

    @Query(
        nativeQuery = true,
        value = """
            SELECT *
            FROM book_statistics
            ORDER BY borrow_count
            LIMIT :rank_limit
        """
    )
    fun findMostBorrowed(@Param(value = "rank_limit") rankLimit: Int): List<BookStatistics>

    @Query(
        nativeQuery = true,
        value = """
            SELECT *
            FROM book_statistics
            ORDER BY average_borrow_time
            LIMIT :rank_limit
        """
    )
    fun findLongestBorrowed(@Param(value = "rank_limit") rankLimit: Int): List<BookStatistics>
}