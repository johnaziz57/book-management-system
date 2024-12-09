package com.john.bookmanagementsystem.feature.user.dto

sealed class RegisterResponse {
    data class Success(val token: String, val type: String = "Bearer") : RegisterResponse()
    data class Failure(val errorMessage: String) : RegisterResponse()
}