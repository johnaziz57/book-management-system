package com.john.bookmanagementsystem.feature.author.repository

import com.john.bookmanagementsystem.feature.author.model.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long> {
    fun findByNameContainingIgnoreCase(nameLike: String): List<Author>
}