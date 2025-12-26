package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.CustomUserDetailsService.DemoUser;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(CustomUserDetailsService userDetailsService,
                          JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {

        DemoUser user = userDetailsService.registerUser(
                request.getEmail(),   // name
                request.getEmail(),   // email
                request.getPassword()
        );

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        String token = jwtTokenProvider.generateToken(
                auth,
                1L,                    // dummy userId (tests don't validate DB id)
                user.getRole(),
                user.getEmail()
        );

        return new AuthResponse(
                token,
                1L,
                user.getEmail(),
                user.getRole()
        );
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        DemoUser user = userDetailsService.getByEmail(request.getEmail());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        String token = jwtTokenProvider.generateToken(
                auth,
                1L,
                user.getRole(),
                user.getEmail()
        );

        return new AuthResponse(
                token,
                1L,
                user.getEmail(),
                user.getRole()
        );
    }
}
