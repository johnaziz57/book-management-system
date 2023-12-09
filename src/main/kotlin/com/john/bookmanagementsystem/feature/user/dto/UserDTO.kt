package com.john.bookmanagementsystem.feature.user.dto

import com.john.bookmanagementsystem.feature.user.model.Role
import com.john.bookmanagementsystem.feature.user.model.User
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


data class userDTO(
    val id: Long?,
    @field:Size(max = 100, message = "Name is too long")
    @field:NotBlank(message = "Name is empty")
    val name: String,

    @field:Size(max = 100, message = "User name is too long")
    @field:NotBlank(message = "User name is empty")
    val userName: String,

    @field:Size(min= 8, message = "Password is too short")
    val password: String
) {
    fun toEntity(): User {
        return id?.let { User(
            id = id,
            name = name,
            userName = userName,
            password = password,
            role = Role.USER
        ) } ?: User(name = name, userName = userName, password = password, role = Role.USER)
    }
}
