package com.john.bookmanagementsystem.feature.user.dto

data class LoginResponseDTO(val token: String, val type: String = "Bearer ")