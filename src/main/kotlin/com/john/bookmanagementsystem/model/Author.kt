package com.john.bookmanagementsystem.model

import jakarta.persistence.*

@Entity
@Table(name = "Author")
data class Author(@Id @GeneratedValue val id: Long, val name: String, @ManyToMany val books: Set<Book>)
