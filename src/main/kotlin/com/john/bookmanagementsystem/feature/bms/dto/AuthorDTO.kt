package com.john.bookmanagementsystem.feature.bms.dto

import com.john.bookmanagementsystem.feature.bms.model.Author
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AuthorDTO(
    val id: Long?,
    @field:Size(max = 100, message = "Name is too long")
    @field:NotBlank(message = "Name is empty")
    val name: String,
) {
    fun toEntity(): Author {
        return id?.let { Author(id = it, name = name) } ?: Author(name = name)
    }
}
