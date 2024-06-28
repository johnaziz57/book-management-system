package com.john.bookmanagementsystem.feature.book.repository

import com.john.bookmanagementsystem.feature.book.model.BorrowLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BorrowLogRepository : JpaRepository<BorrowLog, Long> {

    @Query(
        nativeQuery = true, value = """
        SELECT
            *
        FROM
            borrow_log
        WHERE
            book_id = :book_id
            AND 
            user_id = :user_id
            AND
            returned_date IS NULL
        LIMIT
            1
    """// Order by Date
    )
    fun findFirstUnreturnedBook(
        @Param(value = "book_id") bookId: Long, @Param(value = "user_id") userId: Long
    ): BorrowLog?

    @Query(
        nativeQuery = true, value = """
            SELECT 
                borrow_log.book_id
            FROM borrow_log
            WHERE
                returned_date IS NULL
        """
    )
    fun findNotReturned(): List<Long>
}