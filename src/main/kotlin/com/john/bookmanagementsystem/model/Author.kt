package com.john.bookmanagementsystem.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "Authors")
data class Author(@Id @GeneratedValue val id: Int, val name: String /*val books: List<Book>*/)
