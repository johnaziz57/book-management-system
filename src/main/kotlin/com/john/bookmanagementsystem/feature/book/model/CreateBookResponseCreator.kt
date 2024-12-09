package com.john.bookmanagementsystem.feature.book.model

import com.john.bookmanagementsystem.commons.dto.Response
import com.john.bookmanagementsystem.commons.dto.ResponseCreator
import com.john.bookmanagementsystem.feature.book.dto.BookDTO

sealed class CreateBookResponse : Response
private data class Success(val book: BookDTO) : CreateBookResponse()
private data class Failure(val message: String) : CreateBookResponse()

class CreateBookResponseCreator : ResponseCreator<BookDTO, CreateBookResponse> {

    override fun success(body: BookDTO): CreateBookResponse {
        return Success(body)
    }

    override fun failure(message: String, extra: List<String>): CreateBookResponse {
        return Failure(message)
    }
}