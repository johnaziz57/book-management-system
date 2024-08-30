package com.john.bookmanagementsystem.feature.book.model

sealed class ReturnBookResponse {
    object Success : ReturnBookResponse()

    data class Failure(val message: String) : ReturnBookResponse()
}