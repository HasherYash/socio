package com.socio.socio.service;

import com.socio.socio.dto.*;
import com.socio.socio.entity.Role;
import com.socio.socio.entity.User;
import com.socio.socio.repository.UserRepository;
import com.socio.socio.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsService userDetailsService;

    @Transactional
    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already registered");
        }

        if ("ADMIN".equalsIgnoreCase(request.getRole()) && !request.getEmail().endsWith("@socio.com")) {
            throw new RuntimeException("Only emails ending with @socio.com can be ADMIN");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .isPrivateProfile(false)
                .passwordChangedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return "User registered successfully!";
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password expiry (30 days)
        long daysSinceChange = ChronoUnit.DAYS.between(user.getPasswordChangedAt(), LocalDateTime.now());
        if (daysSinceChange > 30) {
            throw new RuntimeException("Password expired. Please reset your password.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(user.getEmail(), String.valueOf(user.getRole()));

        return new AuthResponse(token, String.valueOf(user.getRole()));
    }

    @Transactional
    public String resetPassword(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordChangedAt(LocalDateTime.now());
        userRepository.save(user);

        return "Password reset successful.";
    }
}