package com.example.demo.security;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long validityMs = 3600000;

    public JwtTokenProvider() {
        this.key = Keys.hmacShaKeyFor(
                "qwertyuiop1234567890zxcvbnm@#$%^".getBytes()
        );
    }

    public JwtTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Authentication authentication,
                                Long userId,
                                String role,
                                String email) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMs);

        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
