package com.john.bookmanagementsystem.feature.user

import com.john.bookmanagementsystem.feature.user.dto.userDTO
import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController constructor(
    @Autowired private val  authenticationManager: AuthenticationManager,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val  passwordEncoder: PasswordEncoder
) {

    @PostMapping("register")
    fun register(@RequestBody @Valid userDTO: userDTO): ResponseEntity<String> {
        if (userRepository.existsByUserName(userDTO.userName)) {
            return ResponseEntity.badRequest().body("Username is already take")
        }

        userRepository.save(userDTO.toEntity())
        return ResponseEntity.ok("Success")
    }
}