package com.socio.user_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserProfileDto {
    private Long id;
    private String name;
    private String email;
    private boolean profilePrivate;
    private Set<Long> following;
}