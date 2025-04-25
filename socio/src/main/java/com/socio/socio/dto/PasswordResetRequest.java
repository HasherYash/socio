package com.socio.socio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    private String email;
    private String newPassword;
}