package com.john.bookmanagementsystem.feature.book.model

import com.john.bookmanagementsystem.feature.user.model.User
import jakarta.persistence.*

//TODO create a service method that will return who borrowed the books
@Entity
@Table(name = "borrow_log")
data class BorrowLog(
    @Id @GeneratedValue val id: Long = -1,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    val book: Book
)