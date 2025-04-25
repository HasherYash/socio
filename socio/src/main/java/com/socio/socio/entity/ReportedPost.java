package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * ReportedPost is an entity class representing a report on a post.
 * This entity is mapped to the "reported_posts" table in the database.
 *
 * Fields:
 * - id: The unique identifier for the reported post.
 * - reporter: The user who reported the post (many-to-one relationship with User).
 * - reportedPost: The post that was reported (many-to-one relationship with Post).
 * - reason: The reason why the post was reported.
 * - reportedAt: The timestamp when the post was reported.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User reporter;

    @ManyToOne
    private Post reportedPost;

    private String reason;

    private LocalDateTime reportedAt;
}