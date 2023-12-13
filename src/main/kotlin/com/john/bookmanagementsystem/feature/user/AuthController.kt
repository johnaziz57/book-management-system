package com.john.bookmanagementsystem.feature.user

import com.john.bookmanagementsystem.configuration.security.JwtTokenProvider
import com.john.bookmanagementsystem.feature.user.dto.LoginDTO
import com.john.bookmanagementsystem.feature.user.dto.LoginResponseDTO
import com.john.bookmanagementsystem.feature.user.dto.UserDTO
import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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
    @Autowired private val passwordEncoder: PasswordEncoder,
    @Autowired private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("login")
    fun login(@RequestBody @Valid loginDTO: LoginDTO): ResponseEntity<LoginResponseDTO> {
        // TODO understand what I am doing here
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDTO.username, loginDTO.password)
        )
        // TODO understand what does SecurityContextHolder do
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtTokenProvider.provideToken(authentication)
        return ResponseEntity.ok(LoginResponseDTO(token))
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