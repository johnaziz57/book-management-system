package com.john.bookmanagementsystem.feature.bms.model

import jakarta.persistence.*
import org.hibernate.validator.constraints.ISBN

@Entity
@Table(name = "Book")
data class Book(
    @Id @GeneratedValue val id: Long = -1,
    val title: String = "",
    @field:ISBN // this a different validation on the presistance level
    val ISBN: String = "",
    @ManyToMany val authors: Set<Author> = emptySet(),
)