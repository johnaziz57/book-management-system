package com.john.bookmanagementsystem.feature.bms.service

import com.john.bookmanagementsystem.feature.bms.dto.AuthorDTO
import com.john.bookmanagementsystem.feature.bms.repository.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorService(@Autowired private val authorRepository: AuthorRepository) {

    fun findAll(): List<AuthorDTO> {
        return authorRepository.findAll().map { it.toDTO() }
    }
}