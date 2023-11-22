package com.john.bookmanagementsystem.feature.bms.service

import com.john.bookmanagementsystem.commons.ServiceResponseException
import com.john.bookmanagementsystem.feature.bms.dto.BookDTO
import com.john.bookmanagementsystem.feature.bms.model.Book
import com.john.bookmanagementsystem.feature.bms.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BookService(@Autowired private val bookRepository: BookRepository) {
    fun findAll(): List<Book> {
        return bookRepository.findAll()
    }

    fun findById(id: Long): Book {
        bookRepository.findById(id).let {
            if (it.isPresent) {
                return it.get()
            } else {
                throw ServiceResponseException("Book not found", HttpStatus.NOT_FOUND)
            }
        }
    }

    fun createBook(bookDTO: BookDTO): Book {
        return bookRepository.save(bookDTO.toEntity())
    }

    fun removeBook(id: Long) {
        bookRepository.deleteById(id)
    }

    fun updateBook(id: Long, bookDTO: BookDTO) {
        bookRepository.findById(id).ifPresentOrElse({
            bookRepository.save(bookDTO.toEntity())
        }, {
            throw ServiceResponseException("Book not found", HttpStatus.NOT_FOUND)
        })
    }

    fun deleteBook(id: Long) {
        bookRepository.deleteById(id)
    }
}