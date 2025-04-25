package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * UserFollowerStatsDto is a Data Transfer Object (DTO) that represents the follower statistics of a user.
 * It contains the user's username, their follower count, and the date when this data was retrieved or recorded.
 *
 * Fields:
 * - username: The username of the user whose follower statistics are being represented.
 * - followerCount: The total number of followers the user has.
 * - createdAt: The date when the follower statistics were recorded.
 */

@Data
@AllArgsConstructor
public class UserFollowerStatsDto {
    private String username;
    private Long followerCount;
    private Date createdAt;
    
}