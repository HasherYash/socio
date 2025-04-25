package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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