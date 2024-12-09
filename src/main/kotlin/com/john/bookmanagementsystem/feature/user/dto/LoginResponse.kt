package com.john.bookmanagementsystem.feature.user.dto

sealed class LoginResponse {
    data class Success(val token: String, val type: String = "Bearer ") : LoginResponse()

    data class Failure(val errorMessage: String) : LoginResponse()
}