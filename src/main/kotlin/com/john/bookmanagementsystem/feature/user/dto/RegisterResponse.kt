package com.john.bookmanagementsystem.feature.user.dto

sealed class RegisterResponse {
    data class SuccessResponse(val token: String, val type: String = "Bearer") : RegisterResponse()
    data class FailureResponse(val errorMessage: String) : RegisterResponse()
}