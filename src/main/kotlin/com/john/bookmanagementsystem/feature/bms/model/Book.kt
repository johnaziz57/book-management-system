package com.john.bookmanagementsystem.feature.bms.model

import jakarta.persistence.*

@Entity
@Table(name = "Book")
data class Book(
    @Id @GeneratedValue val id: Long = -1,
    val title: String = "",
    val ISBN: String = "",
    @ManyToMany val authors: Set<Author> = emptySet(),
)
