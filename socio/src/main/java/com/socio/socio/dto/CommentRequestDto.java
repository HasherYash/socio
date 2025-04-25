package com.socio.socio.dto;

import lombok.Data;

/**
 * CommentRequestDto is a Data Transfer Object (DTO) used for creating a new comment.
 * It contains the content of the comment to be submitted by the user.
 *
 * Fields:
 * - content: The content of the comment to be posted.
 */

@Data
public class CommentRequestDto {
    private String content;
}