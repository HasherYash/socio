package com.socio.socio.dto;

import lombok.*;

/**
 * RegisterRequest is a Data Transfer Object (DTO) used for registering a new user.
 * It contains the user's email, password, and the role they are being assigned during registration.
 *
 * Fields:
 * - email: The email of the user registering.
 * - password: The password of the user registering.
 * - role: The role assigned to the user during registration (e.g., USER, ADMIN).
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String role;
}