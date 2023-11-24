package com.john.bookmanagementsystem.feature.bms.repository

import com.john.bookmanagementsystem.feature.bms.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository // To notify spring that this is not a normal bean but a repo to be user in services
// provides more complex findBy.. to be used in services
interface BookRepository : JpaRepository<Book, Long> {
    fun findByISBN(isbn: String): Book?
}