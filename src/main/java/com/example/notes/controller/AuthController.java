package com.example.notes.controller;

import com.example.notes.dto.request.RegisterRequestDTO;
import com.example.notes.dto.response.RegisterResponseDTO;
import com.example.notes.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("register")
    public RegisterResponseDTO RegisterUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return authService.registerUser(registerRequestDTO);
    }
}
