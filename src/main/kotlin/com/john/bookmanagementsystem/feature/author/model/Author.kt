package com.john.bookmanagementsystem.feature.author.model

import com.john.bookmanagementsystem.feature.author.dto.AuthorDTO
import com.john.bookmanagementsystem.feature.book.model.Book
import jakarta.persistence.*

@Entity
@Table(name = "Author")
data class Author(
    @Id @GeneratedValue val id: Long = -1,
    val name: String = "",
    @ManyToMany(mappedBy = "authors") var books: Set<Book> = mutableSetOf(),
) {
    fun toDTO(): AuthorDTO {
        // TODO check what will happen if authors have books, would this conversion keep
        // going endlessly
        return AuthorDTO(id = id, name = name)
    }
}
