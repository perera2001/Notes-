package com.example.notes.service.impl;

import com.example.notes.config.AdminConfig;
import com.example.notes.dto.request.LoginRequestDTO;
import com.example.notes.dto.request.RegisterRequestDTO;
import com.example.notes.dto.response.LoginResponseDTO;
import com.example.notes.dto.response.RegisterResponseDTO;
import com.example.notes.entity.Role;
import com.example.notes.entity.User;
import com.example.notes.repo.UserRepository;
import com.example.notes.security.JwtService;
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
    @Autowired
    private JwtService jwtService;
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

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        if(loginRequestDTO.getEmail().equals(AdminConfig.ADMIN_EMAIL)
        && loginRequestDTO.getPassword().equals(AdminConfig.ADMIN_PASSWORD)) {
            String token = jwtService.generateToken(
                    AdminConfig.ADMIN_EMAIL,
                    "ROLE_ADMIN"
            );
           return new LoginResponseDTO(
                   "Admin Login successfully",
                   token
           );
        }
        Optional <User> user = userRepository.findByEmail(loginRequestDTO.getEmail());
        if(user.isEmpty()){
            return new LoginResponseDTO(
                    "Email not found",
                    null
            );
        }
        boolean passwordCorrect=
                passwordEncoder.matches(
                        loginRequestDTO.getPassword(),
                        user.get().getPassword()
                );
        if(!passwordCorrect) {
            return new LoginResponseDTO(
                    "Incorrect password",
                    null
            );
        }
            String token = jwtService.generateToken(
                    user.get().getEmail(),
                    user.get().getRole().name()
            );
            return new LoginResponseDTO(
                    "Login successful",
                    token
            );

    }
}
