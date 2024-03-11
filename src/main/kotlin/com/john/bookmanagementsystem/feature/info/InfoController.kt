package com.john.bookmanagementsystem.feature.info

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class InfoController {

    @Value("\${book.system.environment}")
    lateinit var info: String

    @GetMapping("/info")
    fun getInfo(): ResponseEntity<String> {
        return ResponseEntity.ok(info)
    }
}