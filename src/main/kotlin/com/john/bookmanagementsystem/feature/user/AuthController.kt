package com.john.bookmanagementsystem.feature.user

import com.john.bookmanagementsystem.feature.user.dto.LoginDTO
import com.john.bookmanagementsystem.feature.user.dto.UserDTO
import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController constructor(
    @Autowired private val authenticationManager: AuthenticationManager,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("login")
    fun login(@RequestBody @Valid loginDTO: LoginDTO): ResponseEntity<String> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDTO.username, loginDTO.password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        return ResponseEntity.ok("User signed in success")
    }

    @PostMapping("register")
    fun register(@RequestBody @Valid userDTO: UserDTO): ResponseEntity<String> {
        if (userRepository.existsByUserName(userDTO.username)) {
            return ResponseEntity.badRequest().body("Username is already take")
        }
        userDTO.toEntity().let {
            userRepository.save(it.copy(password = passwordEncoder.encode(it.password)))
        }
        return ResponseEntity.ok("Success")
    }
}