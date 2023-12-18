package com.john.bookmanagementsystem.feature.user.service

import com.john.bookmanagementsystem.commons.ServiceResponseException
import com.john.bookmanagementsystem.configuration.security.JwtTokenProvider
import com.john.bookmanagementsystem.feature.user.dto.AuthResponseDTO
import com.john.bookmanagementsystem.feature.user.dto.LoginDTO
import com.john.bookmanagementsystem.feature.user.dto.UserDTO
import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService constructor(
    @Autowired private val authenticationManager: AuthenticationManager,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val passwordEncoder: PasswordEncoder,
    @Autowired private val jwtTokenProvider: JwtTokenProvider
) {

    fun register(userDTO: UserDTO): AuthResponseDTO {
        if (userRepository.existsByUserName(userDTO.username)) {
            throw ServiceResponseException("Username already exits", HttpStatus.BAD_REQUEST)
        }
        val user = userDTO.toEntity()
        userRepository.save(user.copy(password = passwordEncoder.encode(user.password)))

        return AuthResponseDTO(jwtTokenProvider.generateToken(user.userName))
    }

    fun login(loginDTO: LoginDTO): AuthResponseDTO {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDTO.username, loginDTO.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtTokenProvider.generateToken(loginDTO.username)
        return AuthResponseDTO(token)
    }
}