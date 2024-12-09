package com.john.bookmanagementsystem.feature.user.dto

import com.john.bookmanagementsystem.feature.user.model.Role
import com.john.bookmanagementsystem.feature.user.model.User
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


data class RegisterRequest(
    val id: Long?,
    @field:Size(max = 100, message = "Name is too long")
    @field:NotBlank(message = "Name is empty")
    val name: String,

    @field:Size(max = 100, message = "userName is too long")
    @field:NotBlank(message = "userName is empty")
    val userName: String,

    @field:Size(min = 8, message = "Password is too short")
    val password: String
) {
    fun toUserEntity(): User {
        return User(
            id = id,
            name = name,
            userName = userName,
            password = password,
            role = Role.USER
        )
    }
}
