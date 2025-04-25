package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Post is an entity class representing a post within the application.
 * It is mapped to the "posts" table in the database.
 *
 * Fields:
 * - id: The unique identifier for the post.
 * - content: The content of the post.
 * - createdAt: The timestamp when the post was created.
 * - fileType: The file type (if any) associated with the post.
 * - moderationStatus: The status of the post for moderation.
 * - user: The user who created the post (many-to-one relationship with User).
 * - group: The group where the post was made (many-to-one relationship with Group).
 * - comments: A set of comments associated with the post (one-to-many relationship with Comment).
 * - likes: A set of users who liked the post (many-to-many relationship with User).
 * - reportedPosts: A set of reports associated with the post (one-to-many relationship with ReportedPost).
 * - reported: A flag indicating whether the post has been reported.
 * - reportedBy: The user who reported the post (many-to-one relationship with User).
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getContent() {
        return content;
    }

    private String content;

    private LocalDateTime createdAt;

    private String fileType;

    private String moderationStatus; // For moderation actions

    @ManyToOne
    private User user;

    @ManyToOne
    private Group group;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @ManyToMany
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes;

    @OneToMany(mappedBy = "reportedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReportedPost> reportedPosts;

    public boolean isReported() {
        return reportedPosts != null && !reportedPosts.isEmpty();
    }

    @Column(nullable = false)
    private boolean reported;


    @ManyToOne
    @JoinColumn(name = "reported_by_id")
    private User reportedBy;

}