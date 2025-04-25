package com.socio.socio.dto;

import com.socio.socio.entity.User;
import lombok.*;

/**
 * GroupRequest is a Data Transfer Object (DTO) used for creating a new group.
 * It contains the details of the group to be created, including the name, description, and the user who created the group.
 *
 * Fields:
 * - name: The name of the group.
 * - description: A brief description of the group.
 * - createdBy: The user who is creating the group.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {
    private String name;
    private String description;
    private User createdBy;
}