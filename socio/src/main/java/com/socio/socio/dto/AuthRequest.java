package com.socio.socio.dto;

import lombok.*;

/**
 * AuthRequest is a Data Transfer Object (DTO) used for carrying authentication data.
 * It contains the necessary fields for a user to log in to the soCiO social network application.
 *
 * Fields:
 * - email: The email of the user attempting to log in.
 * - password: The password of the user attempting to log in.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}