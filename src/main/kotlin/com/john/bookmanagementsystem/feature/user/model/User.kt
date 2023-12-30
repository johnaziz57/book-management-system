package com.john.bookmanagementsystem.feature.user.model

import jakarta.persistence.*

@Entity
@Table(name = "person")
data class User(
    @Id @GeneratedValue val id: Long = -1,
    val role: Role = Role.USER,
    val name: String,
    @Column(unique = true)
    val userName: String,
    val password: String,
    var borrowedBooksCount: Int = 0
)