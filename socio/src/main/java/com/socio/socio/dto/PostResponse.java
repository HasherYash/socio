package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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