package com.john.bookmanagementsystem.configuration.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig constructor(@Autowired private val userDetailsService: CustomUserDetailsService) {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "**").permitAll()
            .requestMatchers(HttpMethod.POST, "author").authenticated()
            .and()
            .httpBasic()

        return httpSecurity.build()
    }

    @Bean
    fun users(): UserDetailsService {
        val user = User.builder()
            .username("admin")
            .password("0")
            .roles("ADMIN")
            .build()

        val user2 = User.builder()
            .username("user")
            .password("0")
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user, user2)
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
