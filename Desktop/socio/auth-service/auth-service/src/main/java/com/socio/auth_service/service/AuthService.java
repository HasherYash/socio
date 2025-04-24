package com.socio.auth_service.service;

import com.socio.auth_service.dto.*;
import com.socio.auth_service.model.User;
import com.socio.auth_service.repository.UserRepository;
import com.socio.auth_service.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        User.Role assignedRole = request.getRole() != null ? request.getRole() : User.Role.USER;

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .birthday(request.getBirthday())
                .role(assignedRole)
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var token = jwtTokenUtil.generateToken(user);
        return new AuthResponse(token, user.getRole().name());
    }
}