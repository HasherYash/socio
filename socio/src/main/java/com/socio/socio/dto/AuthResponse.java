package com.socio.socio.dto;

import lombok.*;

/**
 * AuthResponse is a Data Transfer Object (DTO) used for carrying the response data during authentication.
 * It contains the authentication token and the user's role after successful login.
 *
 * Fields:
 * - token: The JWT token issued to the user after successful authentication.
 * - role: The role of the authenticated user (e.g., USER, ADMIN).
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
}