package com.socio.auth_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String name;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING) // Make sure this is used if you're storing enum names
    @Column(name = "role")
    private Role role;

    private boolean profilePrivate;

    public enum Role {
        USER, ADMIN
    }
}