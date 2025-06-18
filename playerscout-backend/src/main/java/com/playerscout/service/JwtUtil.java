package com.playerscout.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

/**
 * JwtUtil handles creating and validating JWT tokens.
 * JWTs are used for stateless authentication.
 */
@Component
public class JwtUtil {
    // Secret key for signing tokens, loaded from application.yml
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Token validity in milliseconds (e.g., 24 hours)
    private final long jwtExpirationMs = 86400000;

    /**
     * Generates a JWT for the given username and role.
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Validates a JWT and returns the claims (data inside the token).
     */
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
