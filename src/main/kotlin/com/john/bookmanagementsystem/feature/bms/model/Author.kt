package com.john.bookmanagementsystem.feature.bms.model

import com.john.bookmanagementsystem.feature.bms.dto.AuthorDTO
import jakarta.persistence.*

@Entity
@Table(name = "Author")
data class Author(
    @Id @GeneratedValue val id: Long = -1,
    val name: String = "",
    @ManyToMany(mappedBy = "authors") val books: Set<Book> = emptySet(),
) {
    fun toDTO(): AuthorDTO {
        // TODO check what will happen if authors have books, would this conversion keep
        // going endlessly
        return AuthorDTO(id = id, name = name)
    }
}
