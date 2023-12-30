package com.john.bookmanagementsystem.feature.book.model

import com.john.bookmanagementsystem.feature.author.model.Author
import com.john.bookmanagementsystem.feature.book.dto.BookDTO
import jakarta.persistence.*
import org.hibernate.validator.constraints.ISBN

@Entity
@Table(name = "Book")
data class Book(
    @Id @GeneratedValue val id: Long = -1,
    val title: String = "",
    @field:ISBN // this a different validation on the persistence level
    @Column(unique = true)
    val ISBN: String = "",

    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "book_author",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    var authors: Set<Author> = mutableSetOf(),
) {
    fun toDTO(): BookDTO {
        // TODO check what will happen if authors have books, would this conversion keep
        // going endlessly
        return BookDTO(id = id, title = title, ISBN = ISBN, authors = authors.map { it.toDTO() }.toSet())
    }
}