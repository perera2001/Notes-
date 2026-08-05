package com.example.notes.service;

import com.example.notes.dto.request.RegisterRequestDTO;
import com.example.notes.dto.response.RegisterResponseDTO;

public interface AuthService {
    RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO);
}
