package com.john.bookmanagementsystem.configuration.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {

    fun generateToken(username: String): String {
        val currentDate = Date()
        val expiryDate = Date(currentDate.time + SecurityConstants.JWT_EXPIRY)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(currentDate)
            .setExpiration(expiryDate)
            .signWith(KEY, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUsernameFromJwt(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(KEY)
            .build()
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    fun validateJWT(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private val KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512)
    }
}