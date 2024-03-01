package com.john.bookmanagementsystem.configuration.security

import com.john.bookmanagementsystem.feature.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

// TODO understand why we need this, why not use a normal user service
@Service
class CustomUserDetailsService(@Autowired private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUserName(username)?.let { user ->
            User.builder()
                .username(user.userName)
                .password(user.password)
                .roles(user.role.name)
                .authorities(SimpleGrantedAuthority("ROLE_${user.role.name}"))
                .build()
            // TODO @Giona, which to return UserNameNotFoundException or ServiceResponseException
            // TODO:1 Design the application for security means do not give insights on user errors
            // TODO:2 Design the application for friendlyness means give many insights
        } ?: throw UsernameNotFoundException("user not found")
    }
}
