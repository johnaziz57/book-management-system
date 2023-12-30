package com.john.bookmanagementsystem.feature.book.repository

import com.john.bookmanagementsystem.feature.book.model.BorrowLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BorrowLogRepository : JpaRepository<BorrowLog, Long> {
    fun findByBookId(bookId: Long): BorrowLog?
}