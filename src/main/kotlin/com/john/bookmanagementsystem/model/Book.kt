package com.john.bookmanagementsystem.model

import jakarta.persistence.*

@Entity
@Table(name = "Book")
data class Book(
    @Id @GeneratedValue val id: Long,
    val title: String,
    val ISBN: String,
    @ManyToMany val authors: Set<Author>,
)
