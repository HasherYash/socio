package com.socio.socio.dto;

import lombok.*;

/**
 * PasswordResetRequest is a Data Transfer Object (DTO) used for resetting a user's password.
 * It contains the user's email and the new password they wish to set.
 *
 * Fields:
 * - email: The email of the user requesting the password reset.
 * - newPassword: The new password the user wants to set.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    private String email;
    private String newPassword;
}