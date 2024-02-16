package com.john.bookmanagementsystem.feature.book.model

import com.john.bookmanagementsystem.feature.user.model.User
import jakarta.persistence.*
import java.time.LocalDateTime

//TODO create a service method that will return who borrowed the books
@Entity
@Table(name = "borrow_log")
data class BorrowLog(
    @Id @GeneratedValue val id: Long? = null,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    val book: Book,
    val borrowedDate: LocalDateTime,
    val returnedDate: LocalDateTime?
)