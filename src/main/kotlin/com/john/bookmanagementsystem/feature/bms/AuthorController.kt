package com.john.bookmanagementsystem.feature.bms

import com.john.bookmanagementsystem.feature.bms.dto.AuthorDTO
import com.john.bookmanagementsystem.feature.bms.service.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/authors")
@Validated
class AuthorController(@Autowired private val authorService: AuthorService) {
    @GetMapping
    fun findAll(): ResponseEntity<List<AuthorDTO>> {
        return ResponseEntity.ok(authorService.findAll())
    }

    @GetMapping("/findByName")
    fun findByNameLike(@RequestParam name: String): ResponseEntity<List<AuthorDTO>> {
        return ResponseEntity.ok(authorService.findByNameLike(name))
    }

    @PostMapping("/create")
    fun createAuthor(@RequestBody @Valid authorDTO: AuthorDTO): ResponseEntity<AuthorDTO> {
        return ResponseEntity.ok(authorService.createAuthor(authorDTO))
    }
}