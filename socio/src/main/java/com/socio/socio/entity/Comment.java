package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Comment is an entity class that represents a comment on a post within the application.
 * It is mapped to the "comments" table in the database. Each comment is associated with a specific post
 * and a user who authored the comment. The class includes details such as the comment's content, timestamps,
 * and relationships with the post and user entities.
 *
 * Fields:
 * - id: The unique identifier for the comment.
 * - content: The content of the comment, with a maximum length of 1000 characters.
 * - post: The post to which this comment belongs (many-to-one relationship with the Post entity).
 * - user: The user who authored the comment (many-to-one relationship with the User entity).
 * - createdAt: The timestamp when the comment was created.
 * - updatedAt: The timestamp when the comment was last updated.
 */

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}