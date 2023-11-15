package com.john.bookmanagementsystem.feature.bms

import com.john.bookmanagementsystem.commons.ServiceResponseException
import com.john.bookmanagementsystem.commons.dto.ErrorServiceResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice // This is another presentation layer helper, not necessary attached to same rest controller
@Order(Ordered.HIGHEST_PRECEDENCE) // order of handling the exceptions, highest means first one to handle exception
class BookControllerAdvice {

    // tying this ControllerAdvice to every exception of type ServiceResponseException
    @ExceptionHandler(ServiceResponseException::class)
    fun handleServiceResponseException(exception: ServiceResponseException): ResponseEntity<ErrorServiceResponse> {
        return ResponseEntity
            .status(exception.httpStatus)
            .body(
                ErrorServiceResponse(message = exception.message)
            )
    }
}