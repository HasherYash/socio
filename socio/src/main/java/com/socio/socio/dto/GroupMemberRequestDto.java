package com.socio.socio.dto;

import lombok.Data;

/**
 * GroupMemberRequestDto is a Data Transfer Object (DTO) used for adding or removing users from a group.
 * It contains the username of the user to be added or removed from a group.
 *
 * Fields:
 * - username: The username of the user being added or removed from the group.
 */

@Data
public class GroupMemberRequestDto {
    private String username;
}