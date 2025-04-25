package com.socio.socio.controller;

import com.socio.socio.dto.*;
import com.socio.socio.entity.User;
import com.socio.socio.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController handles the authentication-related operations for the soCiO social network application.
 * It provides endpoints for user registration, login, and password reset.
 * The controller interacts with the AuthService to perform the necessary business logic.
 *
 * Endpoints:
 * - POST /api/auth/register: Registers a new user.
 * - POST /api/auth/login: Logs in an existing user and returns an authentication token.
 * - POST /api/auth/reset-password: Resets the user's password.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest request) {
        return authService.resetPassword(request);
    }
}