package com.john.bookmanagementsystem.presistance

import com.john.bookmanagementsystem.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long>