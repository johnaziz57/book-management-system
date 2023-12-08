package com.john.bookmanagementsystem.feature.user.repository

import com.john.bookmanagementsystem.feature.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUserName(userName: String): User?

    fun existsByUserName(userName: String): Boolean
}