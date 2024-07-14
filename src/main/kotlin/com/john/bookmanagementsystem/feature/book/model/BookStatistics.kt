package com.john.bookmanagementsystem.feature.book.model

import jakarta.persistence.*
import java.time.Duration
import java.time.LocalDateTime

@Entity
@Table(
    name = "book_statistics",
    indexes = [Index(columnList = "borrowCount")]
)
data class BookStatistics(
    @Id
    @GeneratedValue
    val id: Long? = null,
    @OneToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    val book: Book,
    val borrowCount: Int = 0,
    val averageBorrowTime: Int = 0 //In seconds
)

/**
 * Recalculates statics such as borrowCount and averageBorrowTime and returns a new
 * BookStatistics object.
 * If not DateTime provided, it will calculate the average borrow time assuming that
 * the book was just returned
 */
fun BookStatistics.recalculateAfterReturn(
    borrowDate: LocalDateTime,
    returnedDate: LocalDateTime = LocalDateTime.now()
): BookStatistics {
    val previousBorrowCount = borrowCount - 1
    val newAverageBorrowTime = (
            (averageBorrowTime * previousBorrowCount) +
                    Duration.between(borrowDate, returnedDate).seconds
            ) / borrowCount
    return copy(averageBorrowTime = newAverageBorrowTime.toInt())
}

/**
 * Recalculates statics such as borrowCount and averageBorrowTime and returns a new
 * BookStatistics object.
 */
fun BookStatistics.recalculateAfterBorrow(): BookStatistics {
    return copy(borrowCount = borrowCount + 1)
}
