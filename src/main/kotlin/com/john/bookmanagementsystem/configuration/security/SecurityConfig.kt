package com.john.bookmanagementsystem.configuration.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Autowired private val jwtAuthEntryPoint: JwtAuthEntryPoint,
    @Autowired private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            // Set allowed origins
            allowedOrigins = listOf("http://localhost:8081") // Allow requests from this origin
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
            allowedHeaders = listOf("*") // Allow all headers
            allowCredentials = true // Allow credentials (cookies, authorization headers)
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration) // Apply CORS configuration to all paths
        return source
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf {
                it.disable()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(jwtAuthEntryPoint)
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .cors{}
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, "auth/**").permitAll()
                it.requestMatchers(HttpMethod.GET, "**").permitAll()
                it.requestMatchers(HttpMethod.POST, "book/**").authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
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
