package com.john.bookmanagementsystem.feature.bms.repository

import com.john.bookmanagementsystem.feature.bms.model.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long>