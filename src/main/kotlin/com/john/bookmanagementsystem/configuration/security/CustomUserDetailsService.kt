package com.john.bookmanagementsystem.configuration.security

import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(@Autowired private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUserName(username)?.let { user ->
            User.builder()
                .username(user.userName)
                .password(user.password)
                .roles(user.role.toString())
                .build()
            // TODO @Giona, why not use UserNameNotFoundException directly instead of ServiceResponseException
        } ?: throw UsernameNotFoundException("user not found")
    }
}