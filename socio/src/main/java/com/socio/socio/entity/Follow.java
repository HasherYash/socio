package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Follow is an entity class representing a relationship where one user follows another user within the application.
 * It is mapped to the "follows" table in the database. The relationship between the follower and the following user
 * is represented by this entity, with a unique constraint to prevent duplicate follow relationships.
 *
 * Fields:
 * - id: The unique identifier for the follow relationship.
 * - follower: The user who is following another user (many-to-one relationship with the User entity).
 * - following: The user who is being followed by the follower (many-to-one relationship with the User entity).
 *
 * Unique Constraint:
 * - A unique constraint is applied on the combination of follower_id and following_id to ensure that a user cannot
 *   follow the same user more than once.
 */

@Entity
@Table(
        name = "follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following;
}