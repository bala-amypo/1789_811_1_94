package com.example.demo.security;

import org.springframework.security.core.Authentication;

import java.util.UUID;

public class JwtTokenProvider {

    private final String secret;

    public JwtTokenProvider(String secret) {
        this.secret = secret;
    }

    public String generateToken(Authentication auth, Long id, String role, String email) {
        return UUID.randomUUID().toString() + "." + email + "." + role;
    }
}
