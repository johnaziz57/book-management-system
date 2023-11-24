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

    fun findByNameLike(nameLike: String): List<AuthorDTO> {
        return authorRepository.findByNameLike(nameLike).map { it.toDTO() }
    }

    fun createAuthor(authorDTO: AuthorDTO): AuthorDTO {
        return authorRepository.save(authorDTO.toEntity()).toDTO()
    }
}