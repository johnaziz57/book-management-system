package com.john.bookmanagementsystem.feature.book.model

import com.john.bookmanagementsystem.feature.author.model.Author
import com.john.bookmanagementsystem.feature.book.dto.BookDTO
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.hibernate.validator.constraints.ISBN
import java.time.LocalDateTime

@Entity
@Table(name = "book")
data class Book(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val title: String = "",
    @field:ISBN // this a different validation on the persistence level
    @Column(unique = true, name = "isbn")
    val ISBN: String = "",
    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(
        name = "book_author",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    var authors: Set<Author> = mutableSetOf(),

    // TODO check if later I can change default value to 0
    // Good normalization
    val availableCopies: Int = 1,
    val publishDate: LocalDateTime? = null,
) {
    fun toDTO(): BookDTO {
        // TODO check what will happen if authors have books, would this conversion keep
        // going endlessly
        return BookDTO(id = id, title = title, ISBN = ISBN, authors = authors.map { it.toDTO() }.toSet())
    }
}
