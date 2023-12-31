package com.john.bookmanagementsystem.feature.book

import com.john.bookmanagementsystem.feature.book.dto.BookDTO
import com.john.bookmanagementsystem.feature.book.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/book")
@Validated
class BookController(@Autowired private val bookService: BookService) {

    @GetMapping("/all")
    fun findAll(): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.findAll())
    }

    // TODO add @Transactional
    // TODO handle if passing a DTO but the authors are not yet in the Database
    @PostMapping("/create")
    fun createBook(@RequestBody @Valid bookDTO: BookDTO): ResponseEntity<BookDTO> {
        // @Valid will look for  validation in the DTO class
        return ResponseEntity.ok(bookService.createBook(bookDTO))
    }

    @GetMapping("findByTitle")
    fun findBookByTitle(@RequestParam title: String): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.findByTitle(title))
    }

    @PostMapping("/borrow")
    fun borrowBook(@RequestParam bookId: String): ResponseEntity<Unit> {
        return if (bookService.borrowBook(bookId = bookId.toLong())) {
            ResponseEntity.ok(Unit)
        } else {
            ResponseEntity.badRequest().body(Unit)
        }
    }

    @PostMapping("/return")
    fun returnBook(@RequestParam bookId: String): ResponseEntity<Unit> {
        return if (bookService.returnBook(bookId = bookId.toLong())) {
            ResponseEntity.ok(Unit)
        } else {
            ResponseEntity.badRequest().body(Unit)
        }
    }
}