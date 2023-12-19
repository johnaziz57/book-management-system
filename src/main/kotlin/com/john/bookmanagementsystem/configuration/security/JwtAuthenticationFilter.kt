package com.john.bookmanagementsystem.configuration.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter constructor(
    @Autowired private val jwtTokenProvider: JwtTokenProvider,
    @Autowired private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    // TODO understand what does this filter do
    // It doesn't feel that much of a filter as much as it is an interceptor to translate JWT to authentication token
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        getJwtToken(request)?.let { token ->
            if (!jwtTokenProvider.validateJWT(token)) return@let
            val username = jwtTokenProvider.getUsernameFromJwt(token)
            val userDetails = customUserDetailsService.loadUserByUsername(username)
            val authenticationToken = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )

            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
        filterChain.doFilter(request, response)

    }

    private fun getJwtToken(request: HttpServletRequest): String? {
        val header: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        return header?.substringAfter(TOKEN_BEARER_PREFIX, "")
    }


    companion object {
        private const val TOKEN_BEARER_PREFIX = "Bearer "
    }
}