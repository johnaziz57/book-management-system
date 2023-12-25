package com.john.bookmanagementsystem.commons

import com.john.bookmanagementsystem.commons.dto.ErrorServiceResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice // This is another presentation layer helper, not necessary attached to same rest controller
@Order(Ordered.HIGHEST_PRECEDENCE) // order of handling the exceptions, highest means first one to handle exception
class BookManagementSystemControllerAdvice {

    // tying this ControllerAdvice to every exception of type ServiceResponseException
    @ExceptionHandler(ServiceResponseException::class)
    fun handleServiceResponseException(exception: ServiceResponseException): ResponseEntity<ErrorServiceResponse> {
        return ResponseEntity
            .status(exception.httpStatus)
            .body(
                ErrorServiceResponse(message = exception.message)
            )
    }

    @ExceptionHandler(UnsupportedOperationException::class)
    fun handleUnsupportedOperationException(exception: UnsupportedOperationException): ResponseEntity<ErrorServiceResponse> {
        // TODO add a condition for when in PROD don't return the exception message
        throw exception
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorServiceResponse(exception.message ?: exception.localizedMessage)
            )
    }
}