package com.example.notes.service;

import com.example.notes.dto.request.LoginRequestDTO;
import com.example.notes.dto.request.RegisterRequestDTO;
import com.example.notes.dto.response.LoginResponseDTO;
import com.example.notes.dto.response.RegisterResponseDTO;

public interface AuthService {
    RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
