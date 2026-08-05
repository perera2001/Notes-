package com.example.notes.service.impl;

import com.example.notes.dto.request.RegisterRequestDTO;
import com.example.notes.dto.response.RegisterResponseDTO;
import com.example.notes.entity.Role;
import com.example.notes.entity.User;
import com.example.notes.repo.UserRepository;
import com.example.notes.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceIMPL implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequestDTO.getEmail());
        if (existingUser.isPresent()) {
            return new RegisterResponseDTO("Email already exists!");
        }
        User user = new User();
        user.setName(registerRequestDTO.getName());
        user.setEmail(registerRequestDTO.getEmail());
        String encryptedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);

        return new RegisterResponseDTO("Successfully registered!");
    }
}
