package com.john.bookmanagementsystem.feature.bms

import com.john.bookmanagementsystem.feature.bms.model.Book
import com.john.bookmanagementsystem.feature.bms.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(@Autowired private val bookService: BookService) {

    @GetMapping
    fun findAll(): List<Book> {
        return bookService.findAll()
    }
}