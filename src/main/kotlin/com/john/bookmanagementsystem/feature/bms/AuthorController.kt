package com.john.bookmanagementsystem.feature.bms

import com.john.bookmanagementsystem.feature.bms.dto.AuthorDTO
import com.john.bookmanagementsystem.feature.bms.service.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Validated
class AuthorController(@Autowired private val authorService: AuthorService) {
    @GetMapping
    fun findAll(): ResponseEntity<List<AuthorDTO>> {
        return ResponseEntity.ok(authorService.findAll())
    }
}