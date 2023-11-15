package com.john.bookmanagementsystem.feature.bms.dto

import com.john.bookmanagementsystem.feature.bms.model.Book

data class BookDTO(val id: Long?, val title: String, val ISBN: String, val authors: Set<AuthorDTO>) {
    fun toEntity(): Book {
        val mappedAuthors = authors.map { it.toEntity() }.toSet()
        return id?.let {
            Book(id = id, title = title, ISBN = ISBN, authors = mappedAuthors)
        } ?: Book(title = title, ISBN = ISBN, authors = mappedAuthors)
    }
}
