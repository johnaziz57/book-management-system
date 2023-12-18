package com.john.bookmanagementsystem.feature.user

import com.john.bookmanagementsystem.feature.user.dto.AuthResponseDTO
import com.john.bookmanagementsystem.feature.user.dto.LoginDTO
import com.john.bookmanagementsystem.feature.user.dto.UserDTO
import com.john.bookmanagementsystem.feature.user.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController constructor(
    @Autowired private val authService: AuthService,
) {

    @PostMapping("login")
    fun login(@RequestBody @Valid loginDTO: LoginDTO): ResponseEntity<AuthResponseDTO> {
        return ResponseEntity.ok(authService.login(loginDTO))
    }

    @PostMapping("register")
    fun register(@RequestBody @Valid userDTO: UserDTO): ResponseEntity<AuthResponseDTO> {
        return ResponseEntity.ok(authService.register(userDTO))
    }
}