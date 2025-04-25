package com.socio.socio.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * CommentResponseDto is a Data Transfer Object (DTO) used for representing a comment's details in the response.
 * It contains the comment's ID, content, author, and creation timestamp.
 *
 * Fields:
 * - id: The unique identifier of the comment.
 * - content: The content of the comment.
 * - author: The username of the user who posted the comment.
 * - createdAt: The timestamp when the comment was created.
 */

@Data
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}