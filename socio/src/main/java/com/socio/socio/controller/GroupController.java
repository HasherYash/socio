package com.socio.socio.controller;

import com.socio.socio.dto.GroupMemberRequestDto;
import com.socio.socio.dto.GroupRequest;
import com.socio.socio.entity.Group;
import com.socio.socio.entity.User;
import com.socio.socio.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

/**
 * GroupController manages the group-related operations for the soCiO social network application.
 * It provides endpoints for creating groups, joining and leaving groups, managing group members,
 * searching for groups, and retrieving group details.
 * The controller interacts with the GroupService to handle the business logic for these operations.
 *
 * Endpoints:
 * - POST /api/groups: Creates a new group.
 * - POST /api/groups/{groupId}/join: Allows a user to join a group by email.
 * - POST /api/groups/{groupId}/leave: Allows a user to leave a group by email.
 * - GET /api/groups/{groupId}: Retrieves details of a specified group.
 * - GET /api/groups/{groupId}/members: Retrieves the members of a specified group.
 * - POST /api/groups/{groupId}/add-member: Adds a user to a specified group.
 * - DELETE /api/groups/{groupId}/remove-member: Removes a user from a specified group.
 * - GET /api/groups/search: Searches for groups by a search term.
 * - GET /api/groups/user/{userId}: Retrieves all groups a user is a part of.
 * - GET /api/groups/all/: Retrieves a paginated and sorted list of all groups.
 */

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public Group createGroup(@RequestBody GroupRequest request) {
        return groupService.createGroup(request);
    }

    @PostMapping("/{groupId}/join")
    public Group joinGroup(@PathVariable Long groupId, @RequestParam String email) {
        return groupService.joinGroup(groupId, email);
    }

    @PostMapping("/{groupId}/leave")
    public Group leaveGroup(@PathVariable Long groupId, @RequestParam String email) {
        return groupService.leaveGroup(groupId, email);
    }

    @GetMapping("/{groupId}")
    public Group getGroupDetails(@PathVariable Long groupId) {
        return groupService.getGroupDetails(groupId);
    }

    @GetMapping("/{groupId}/members")
    public Set<User> getGroupMembers(@PathVariable Long groupId) {
        return groupService.getGroupMembers(groupId);
    }

    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<String> addUserToGroup(
            @PathVariable Long groupId,
            @RequestBody GroupMemberRequestDto dto,
            Authentication auth
    ) {
        groupService.addUserToGroup(groupId, auth.getName(), dto.getUsername());
        return ResponseEntity.ok("User added to group.");
    }

    @DeleteMapping("/{groupId}/remove-member")
    public ResponseEntity<String> removeUserFromGroup(
            @PathVariable Long groupId,
            @RequestBody GroupMemberRequestDto dto,
            Authentication auth
    ) {
        groupService.removeUserFromGroup(groupId, auth.getName(), dto.getUsername());
        return ResponseEntity.ok("User removed from group.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Group>> searchGroups(@RequestParam String searchTerm) {
        List<Group> groups = groupService.searchGroups(searchTerm);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Group>> getUserGroups(@PathVariable Long userId) {
        List<Group> groups = groupService.getGroupsForUser(userId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/all/")
    public ResponseEntity<Page<Group>> getAllGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(groupService.getAllGroups(pageable));
    }
}