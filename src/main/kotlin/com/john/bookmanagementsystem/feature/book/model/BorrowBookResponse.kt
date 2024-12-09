package com.john.bookmanagementsystem.feature.book.model

// TODO since a lot of responses will have SUCCESS and FAILURE, maybe it should be an interface and those classes
// can have success and failure methods to override
sealed class BorrowBookResponse {
    object Success : BorrowBookResponse()

    data class Failure(val message: String) : BorrowBookResponse()
}