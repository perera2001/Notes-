package com.example.notes.security;

import com.example.notes.config.AdminConfig;
import com.example.notes.entity.Role;
import com.example.notes.entity.User;
import com.example.notes.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // Check the hard-coded admin
        if (email.equals(AdminConfig.ADMIN_EMAIL)) {

            User admin = new User();

            admin.setEmail(AdminConfig.ADMIN_EMAIL);
            admin.setPassword(AdminConfig.ADMIN_PASSWORD);
            admin.setRole(Role.ROLE_ADMIN);

            return new CustomUserDetails(admin);
        }

        // Search for a normal user in the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        return new CustomUserDetails(user);
    }
}