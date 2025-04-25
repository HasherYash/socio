package com.socio.socio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowDto {
    private String username;
}