package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * User is an entity class representing a user within the application.
 * It is mapped to the "users" table in the database.
 *
 * Fields:
 * - id: The unique identifier for the user.
 * - email: The user's email (unique).
 * - password: The user's password.
 * - role: The user's role (USER or ADMIN).
 * - isPrivateProfile: A flag indicating whether the user's profile is private.
 * - dateOfBirth: The user's date of birth.
 * - passwordChangedAt: The timestamp when the user's password was last changed.
 * - groups: A set of groups the user is part of (many-to-many relationship with Group).
 * - username: The user's username (unique).
 * - createdAt: The timestamp when the user was created.
 *
 * Methods:
 * - onCreate(): Sets the createdAt field before the user is persisted to the database.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isPrivateProfile;
    private LocalDate dateOfBirth;
    private LocalDateTime passwordChangedAt;

    @ManyToMany(mappedBy = "users")
    @JsonBackReference
    private Set<Group> groups;

    @Column(unique = true)
    private String username;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}