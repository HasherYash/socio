package com.socio.socio.dto;

import lombok.*;

/**
 * PostRequest is a Data Transfer Object (DTO) used for creating a new post.
 * It contains the content of the post, the email of the user creating the post, and the ID of the group (if applicable).
 *
 * Fields:
 * - content: The content of the post.
 * - userEmail: The email of the user creating the post.
 * - groupId: The ID of the group in which the post is being created (optional).
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String content;
    private String userEmail;
    private Long groupId;
}