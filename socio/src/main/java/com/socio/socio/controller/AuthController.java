package com.socio.socio.controller;

import com.socio.socio.dto.*;
import com.socio.socio.entity.User;
import com.socio.socio.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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