package com.socio.socio.controller;

import com.socio.socio.dto.GroupMemberRequestDto;
import com.socio.socio.dto.GroupRequest;
import com.socio.socio.entity.Group;
import com.socio.socio.entity.User;
import com.socio.socio.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

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
}