package com.john.bookmanagementsystem.feature.book

import com.john.bookmanagementsystem.feature.book.dto.BookDTO
import com.john.bookmanagementsystem.feature.book.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/book")
@Validated
class BookController(@Autowired private val bookService: BookService) {

    @GetMapping("/all")
    fun findAll(@RequestParam(required = false) page: Int?): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.findAll(PageRequest.of(page ?: 0, 5)))
    }

    // TODO add @Transactional
    // TODO handle if passing a DTO but the authors are not yet in the Database
    @PostMapping("/create")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    fun createBook(@RequestBody @Valid bookDTO: BookDTO): ResponseEntity<BookDTO> {
        // @Valid will look for  validation in the DTO class
        return ResponseEntity.ok(bookService.createBook(bookDTO))
    }

    @GetMapping("findByTitle")
    fun findBookByTitle(@RequestParam title: String): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.findByTitle(title))
    }

    @PostMapping("/borrow")
    @PreAuthorize(value = "hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    fun borrowBook(@RequestParam bookId: String): ResponseEntity<Unit> {
        return if (bookService.borrowBook(bookId = bookId.toLong())) {
            ResponseEntity.ok(Unit)
        } else {
            ResponseEntity.badRequest().body(Unit)
        }
    }

    @PostMapping("/return")
    @PreAuthorize(value = "hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    fun returnBook(@RequestParam bookId: String): ResponseEntity<Unit> {
        return if (bookService.returnBook(bookId = bookId.toLong())) {
            ResponseEntity.ok(Unit)
        } else {
            ResponseEntity.badRequest().body(Unit)
        }
    }
}