package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Group is an entity class representing a group within the application.
 * It is mapped to the "groups" table in the database. The class contains information about the group's name,
 * description, users who are part of the group, and the user who created the group.
 *
 * Fields:
 * - id: The unique identifier for the group.
 * - name: The name of the group.
 * - description: A description of the group.
 * - users: A set of users associated with the group (many-to-many relationship with the User entity).
 * - members: A set of users who are members of the group (many-to-many relationship with the User entity).
 * - createdBy: The user who created the group (many-to-one relationship with the User entity).
 *
 * Join Tables:
 * - user_groups: A many-to-many relationship table between users and groups to associate users with the group.
 * - group_members: A many-to-many relationship table specifically representing the group members.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`groups`")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
    name = "group_members",
    joinColumns = @JoinColumn(name = "group_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

}