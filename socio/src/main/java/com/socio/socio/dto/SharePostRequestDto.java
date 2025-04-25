package com.socio.socio.dto;

import lombok.Data;

/**
 * SharePostRequestDto is a Data Transfer Object (DTO) used for sharing a post.
 * It contains an optional additional text that the user can include when sharing the post.
 *
 * Fields:
 * - additionalText: The message that the user adds when sharing the post.
 */

@Data
public class SharePostRequestDto {
    private String additionalText;
}