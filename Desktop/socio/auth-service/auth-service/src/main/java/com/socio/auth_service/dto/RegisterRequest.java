package com.socio.auth_service.dto;

import com.socio.auth_service.model.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private LocalDate birthday;
    private User.Role role;
}