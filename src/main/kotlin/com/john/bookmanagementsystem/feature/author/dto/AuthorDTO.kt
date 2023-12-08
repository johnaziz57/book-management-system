package com.john.bookmanagementsystem.feature.author.dto

import com.john.bookmanagementsystem.feature.author.model.Author
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AuthorDTO(
    @Id @GeneratedValue
    val id: Long = -1,
    @field:Size(max = 100, message = "Name is too long")
    @field:NotBlank(message = "Name is empty")
    @Column(unique = true)
    val name: String,
) {
    fun toEntity(): Author {
        return Author(id = id, name = name)
    }
}
