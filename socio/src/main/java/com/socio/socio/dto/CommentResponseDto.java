package com.socio.socio.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}