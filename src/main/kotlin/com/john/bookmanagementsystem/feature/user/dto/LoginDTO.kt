package com.john.bookmanagementsystem.feature.user.dto

import javax.validation.constraints.NotBlank

data class LoginDTO(
    @field:NotBlank(message = "Username is empty")
    val username: String,
    @field:NotBlank(message = "Password is empty")
    val password: String
)
