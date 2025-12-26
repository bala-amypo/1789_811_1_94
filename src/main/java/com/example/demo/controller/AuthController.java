package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public Map<String, Object> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // In real app, hash this
        userRepository.save(user);

        return Map.of(
                "message", "User registered successfully",
                "id", user.getId(),
                "email", user.getEmail(),
                "role", user.getRole()
        );
    }
}
