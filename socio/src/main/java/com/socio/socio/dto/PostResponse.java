package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * PostResponse is a Data Transfer Object (DTO) used for representing a post's details in the response.
 * It contains the post's ID, content, visibility status, author email, creation timestamp, and group ID (if applicable).
 *
 * Fields:
 * - id: The unique identifier of the post.
 * - content: The content of the post.
 * - isPrivate: A flag indicating whether the post is private.
 * - authorEmail: The email of the author who created the post.
 * - createdAt: The timestamp when the post was created.
 * - groupId: The ID of the group where the post was created (optional).
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private Long id;

    @Getter
    private String content;
    private boolean isPrivate;
    private String authorEmail;
    private LocalDateTime createdAt;

    @Getter
    private Long groupId;

}