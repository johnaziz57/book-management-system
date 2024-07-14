package com.john.bookmanagementsystem.feature.user.service

import com.john.bookmanagementsystem.configuration.security.JwtTokenProvider
import com.john.bookmanagementsystem.feature.user.dto.LoginRequest
import com.john.bookmanagementsystem.feature.user.dto.LoginResponse
import com.john.bookmanagementsystem.feature.user.dto.RegisterRequest
import com.john.bookmanagementsystem.feature.user.dto.RegisterResponse
import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
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

    fun register(registerRequest: RegisterRequest): RegisterResponse {
        if (userRepository.existsByUserName(registerRequest.username)) {
            return RegisterResponse.Failure("Can't register ${registerRequest.username}. A user with the same username already exists")
        }
        val user = registerRequest.toUserEntity()
        userRepository.save(user.copy(password = passwordEncoder.encode(user.password)))
        return RegisterResponse.Success(jwtTokenProvider.generateToken(user.userName))
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            val token = jwtTokenProvider.generateToken(loginRequest.username)
            return LoginResponse.Success(token)
        } catch (e: Exception) {
            return LoginResponse.Failure("Invalid credentials")
        }
    }
}