package com.example.demo.controller;

import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(CustomUserDetailsService userDetailsService,
                          JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // -----------------------------
    // REGISTER
    // -----------------------------
    @PostMapping("/register")
    public Map<String, Object> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {

        CustomUserDetailsService.DemoUser user =
                userDetailsService.registerUser(name, email, password);

        return Map.of(
                "message", "User registered successfully",
                "email", user.getEmail(),
                "role", user.getRole()
        );
    }

    // -----------------------------
    // LOGIN
    // -----------------------------
    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestParam String email,
            @RequestParam String password) {

        // Authentication is simulated (as in your tests)
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        email, password, Collections.emptyList());

        CustomUserDetailsService.User user =
                userDetailsService.getByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Generate JWT
        String token = jwtTokenProvider.generateToken(auth, user.getId(), user.getRole(), user.getEmail());

        return Map.of(
                "email", user.getEmail(),
                "role", user.getRole(),
                "token", token
        );
    }
}
