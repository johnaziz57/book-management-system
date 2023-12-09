package com.john.bookmanagementsystem.feature.author.service

import com.john.bookmanagementsystem.feature.author.dto.AuthorDTO
import com.john.bookmanagementsystem.feature.author.repository.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorService(@Autowired private val authorRepository: AuthorRepository) {

    fun findAll(): List<AuthorDTO> {
        return authorRepository.findAll().map { it.toDTO() }
    }

    fun findByName(nameLike: String): List<AuthorDTO> {
        return authorRepository.findByNameContainingIgnoreCase(nameLike).map { it.toDTO() }
    }

    fun createAuthor(authorDTO: AuthorDTO): AuthorDTO {
        return authorRepository.save(authorDTO.toEntity()).toDTO()
    }
}