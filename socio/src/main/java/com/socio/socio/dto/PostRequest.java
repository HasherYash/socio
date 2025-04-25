package com.socio.socio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String content;
    private String userEmail;
    private Long groupId;
}