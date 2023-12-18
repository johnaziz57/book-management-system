package com.john.bookmanagementsystem.feature.user.dto

data class AuthResponseDTO(val token: String, val type: String = "Bearer ")