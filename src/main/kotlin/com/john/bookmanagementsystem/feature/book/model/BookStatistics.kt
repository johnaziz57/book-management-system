package com.john.bookmanagementsystem.feature.book.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

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
