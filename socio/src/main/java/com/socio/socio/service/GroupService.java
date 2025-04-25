package com.socio.socio.service;

import com.socio.socio.dto.GroupRequest;
import com.socio.socio.entity.Group;
import com.socio.socio.entity.User;
import com.socio.socio.repository.GroupRepository;
import com.socio.socio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Service for managing groups, including group creation, joining, leaving, and searching.
 * Provides functionality for managing group memberships and posts related to groups.
 *
 * Methods:
 * - createGroup: Creates a new group.
 * - joinGroup: Allows a user to join a specific group.
 * - leaveGroup: Allows a user to leave a group.
 * - getGroupDetails: Retrieves details of a group.
 * - getGroupMembers: Retrieves members of a group.
 * - addUserToGroup: Adds a user to a group.
 * - removeUserFromGroup: Removes a user from a group.
 * - searchGroups: Searches for groups based on a search term.
 * - getGroupsForUser: Retrieves the groups a user is a member of.
 * - getGroupsSorted: Retrieves groups sorted by members and post count.
 * - getAllGroups: Retrieves a paginated list of all groups.
 */

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group createGroup(GroupRequest request) {
        Group group = Group.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(request.getCreatedBy())
                .build();
        return groupRepository.save(group);
    }

    public Group joinGroup(Long groupId, String email) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        group.getUsers().add(user);
        return groupRepository.save(group);
    }

    public Group leaveGroup(Long groupId, String email) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        group.getUsers().remove(user);
        return groupRepository.save(group);
    }

    public Group getGroupDetails(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public Set<User> getGroupMembers(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return group.getUsers();
    }

    public void addUserToGroup(Long groupId, String requesterUsername, String newUsername) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User newUser = userRepository.findByEmail(newUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        group.getUsers().add(newUser);
        groupRepository.save(group);
    }

    public void removeUserFromGroup(Long groupId, String requesterUsername, String removeUsername) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        group.getUsers().removeIf(u -> u.getEmail().equals(removeUsername));
        groupRepository.save(group);
    }

    public List<Group> searchGroups(String searchTerm) {
        return groupRepository.searchGroups(searchTerm);
    }

    public List<Group> getGroupsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return groupRepository.findByUsersContaining(user);
    }

    public List<Group> getGroupsSorted() {
        return groupRepository.getGroupsSortedByMembersAndPosts();
    }

    public Page<Group> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }
}