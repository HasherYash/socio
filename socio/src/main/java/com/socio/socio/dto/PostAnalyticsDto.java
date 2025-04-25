package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostAnalyticsDto {
    private Long postId;
    private String createdBy;
    private LocalDate createdAt;
    private String fileType;
    private boolean inGroup;
    private int likeCount;
    private int commentCount;
}