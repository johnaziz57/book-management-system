package com.john.bookmanagementsystem.feature.user

import com.john.bookmanagementsystem.feature.user.dto.LoginRequest
import com.john.bookmanagementsystem.feature.user.dto.LoginResponse
import com.john.bookmanagementsystem.feature.user.dto.RegisterRequest
import com.john.bookmanagementsystem.feature.user.dto.RegisterResponse
import com.john.bookmanagementsystem.feature.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    @Autowired private val userService: UserService,
) {

    @PostMapping("login")
    fun login(@RequestBody @Valid loginRequest: LoginRequest): LoginResponse {
        return userService.login(loginRequest)
    }

    @PostMapping("register")
    fun register(@RequestBody @Valid registerRequest: RegisterRequest): RegisterResponse {
        return userService.register(registerRequest)
    }
}