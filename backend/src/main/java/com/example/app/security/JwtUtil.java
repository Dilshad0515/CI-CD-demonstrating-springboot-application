package com.example.app.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JWT utility using jjwt.
 * - generateToken: generates access token with configurable expiry (default 1 hour)
 * - validateToken: validates signature and expiration
 * - getUsername: extracts subject
 *
 * IMPORTANT: For production load JWT_SECRET via environment variable and keep it secret.
 */
@Component
public class JwtUtil {

    // TODO: Replace with a secure value via environment variable in production
    private final String SECRET = System.getenv().getOrDefault("JWT_SECRET", "change-this-secret-for-dev-only-changeit");
    private final long EXP_MS = Long.parseLong(System.getenv().getOrDefault("JWT_EXP_MS", String.valueOf(1000L * 60 * 60))); // default 1 hour

    private Key key() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", String.join(",", roles))
                .setExpiration(new Date(System.currentTimeMillis() + EXP_MS))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
    }

    public String getUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }
}
