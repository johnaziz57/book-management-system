package com.john.bookmanagementsystem.feature.bms

import com.john.bookmanagementsystem.feature.bms.dto.BookDTO
import com.john.bookmanagementsystem.feature.bms.model.Book
import com.john.bookmanagementsystem.feature.bms.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/book")
@Validated
class BookController(@Autowired private val bookService: BookService) {

    @GetMapping
    fun findAll(): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.findAll())
    }

    // TODO add @Transactional
    // TODO handle if passing a DTO but the authors are not yet in the Database
    @PostMapping
    fun createBook(@RequestBody @Valid bookDTO: BookDTO): ResponseEntity<Book> {
        // @Valid will look for  validation in the DTO class
        return ResponseEntity.ok(bookService.createBook(bookDTO))
    }
}