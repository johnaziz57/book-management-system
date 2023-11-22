package com.john.bookmanagementsystem.feature.bms.dto

import com.john.bookmanagementsystem.feature.bms.model.Book
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


data class BookDTO(
    val id: Long?,
    @field:Size(max = 100, message = "Title is too long")
    @field:NotBlank(message = "Title is empty")
    val title: String,
    // need @field to instruct that the validation is happening on this field
    // This is related more to Kotlin because of data class different
    @field:Size(min = 10, max = 13, message = "ISBN is not valid")
    val ISBN: String,
    @Valid // to instruct it to validate sub-classes
    val authors: Set<AuthorDTO>,
) {
    fun toEntity(): Book {
        val mappedAuthors = authors.map { it.toEntity() }.toSet()
        return id?.let {
            Book(id = id, title = title, ISBN = ISBN, authors = mappedAuthors)
        } ?: Book(title = title, ISBN = ISBN, authors = mappedAuthors)
    }
}
