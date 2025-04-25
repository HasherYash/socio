package com.socio.socio.dto;

import lombok.Builder;
import lombok.Data;

/**
 * FollowDto is a Data Transfer Object (DTO) used for representing a user's follow details.
 * It contains the username of the user who is being followed or unfollowed.
 *
 * Fields:
 * - username: The username of the user being followed or unfollowed.
 */

@Data
@Builder
public class FollowDto {
    private String username;
}