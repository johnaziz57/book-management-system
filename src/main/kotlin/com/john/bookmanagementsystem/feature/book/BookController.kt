package com.john.bookmanagementsystem.feature.book

import com.john.bookmanagementsystem.configuration.security.UserAuthorization
import com.john.bookmanagementsystem.configuration.security.annotation.AdminAuthorization
import com.john.bookmanagementsystem.feature.book.dto.BookDTO
import com.john.bookmanagementsystem.feature.book.model.BorrowBookResponse
import com.john.bookmanagementsystem.feature.book.model.CreateBookResponse
import com.john.bookmanagementsystem.feature.book.model.ReturnBookResponse
import com.john.bookmanagementsystem.feature.book.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
    @AdminAuthorization
    fun createBook(@RequestBody @Valid bookDTO: BookDTO): CreateBookResponse {
        // @Valid will look for  validation in the DTO class
        return bookService.createBook(bookDTO)
    }

    @GetMapping("find-by-title")
    fun findBookByTitle(@RequestParam title: String): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.findByTitle(title))
    }

    @PostMapping("/borrow")
    @UserAuthorization
    fun borrowBook(@RequestParam bookId: String, userToken: UsernamePasswordAuthenticationToken): BorrowBookResponse {
        return bookService.borrowBook(bookId = bookId.toLong(), userToken.name)
    }

    @PostMapping("/return")
    @UserAuthorization
    fun returnBook(@RequestParam bookId: String, userToken: UsernamePasswordAuthenticationToken): ReturnBookResponse {
        return bookService.returnBook(bookId = bookId.toLong(), userToken.name)
    }

    @GetMapping("/most-borrowed")
    fun getMostBorrowed(
        @RequestParam(
            required = false,
            defaultValue = "1"
        ) rankLimit: Int
    ): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.getMostBorrowed(rankLimit))
    }

    @GetMapping("/longest-borrowed")
    fun getLongestBorrowed(
        @RequestParam(
            required = false,
            defaultValue = "1"
        ) rankLimit: Int
    ): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.getLongestBorrowed(rankLimit))
    }

    @GetMapping("/not-returned")
    fun getNotReturnedBooks(): ResponseEntity<List<BookDTO>> {
        return ResponseEntity.ok(bookService.getNotReturned())
    }
}
