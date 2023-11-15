package com.john.bookmanagementsystem.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "Books")
data class Book(@Id @GeneratedValue val id: Int, val title: String, val ISBN: String /*val authors:List<Author>*/)
