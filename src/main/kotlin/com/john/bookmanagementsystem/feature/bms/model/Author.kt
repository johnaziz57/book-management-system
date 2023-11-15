package com.john.bookmanagementsystem.feature.bms.model

import jakarta.persistence.*

@Entity
@Table(name = "Author")
data class Author(
    @Id @GeneratedValue val id: Long = -1,
    val name: String = "",
    @ManyToMany val books: Set<Book> = emptySet(),
)
