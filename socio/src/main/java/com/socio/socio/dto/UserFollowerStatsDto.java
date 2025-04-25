package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserFollowerStatsDto {
    private String username;
    private Long followerCount;
    private Date createdAt;
    
}