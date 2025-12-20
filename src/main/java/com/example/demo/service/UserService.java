package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public interface UserService {

    User registerUser(String fullName, String email, String password);

    User getByEmail(String email);

    boolean exists(String email);
}