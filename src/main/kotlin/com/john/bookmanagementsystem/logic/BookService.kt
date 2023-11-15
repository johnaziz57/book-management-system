package com.john.bookmanagementsystem.logic

import com.john.bookmanagementsystem.model.Book
import com.john.bookmanagementsystem.presistance.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookService(@Autowired private val bookRepository: BookRepository) {
    fun findAll(): List<Book> {
        return bookRepository.findAll()
    }
}