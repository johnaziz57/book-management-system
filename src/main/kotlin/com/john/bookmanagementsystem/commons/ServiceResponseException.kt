package com.john.bookmanagementsystem.commons

import org.springframework.http.HttpStatus

data class ServiceResponseException(override val message: String, val httpStatus: HttpStatus) :
    RuntimeException(message)
