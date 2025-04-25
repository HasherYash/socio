package com.socio.socio.dto;

import lombok.Data;

/**
 * ReportPostRequestDto is a Data Transfer Object (DTO) used for reporting a post.
 * It contains the reason for reporting the post.
 *
 * Fields:
 * - reason: The reason for reporting the post.
 */

@Data
public class ReportPostRequestDto {
    private String reason;
}