package com.john.bookmanagementsystem.feature.bms.dto

import com.john.bookmanagementsystem.feature.bms.model.Author

data class AuthorDTO(val id: Long?, val name: String) {
    fun toEntity(): Author {
        return id?.let { Author(id = it, name = name) } ?: Author(name = name)
    }
}
