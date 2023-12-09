package com.john.bookmanagementsystem.feature.user.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "Person")
data class User(
    @Id @GeneratedValue val id: Long = -1,
    val role: Role,
    val name: String,
    val userName: String,
    val password: String
)