package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Like is an entity class representing a "like" action on a post.
 * This entity is mapped to the "likes" table in the database.
 *
 * Fields:
 * - id: The unique identifier for the like.
 * - user: The user who liked the post (many-to-one relationship with User).
 * - post: The post that was liked (many-to-one relationship with Post).
 * - likedAt: The timestamp when the like was created.
 *
 * Constraints:
 * - A unique constraint is applied on the combination of user_id and post_id to ensure that a user can like a post only once.
 */

@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private LocalDateTime likedAt;
}