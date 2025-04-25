package com.socio.socio.controller;

import com.socio.socio.dto.PostAnalyticsDto;
import com.socio.socio.dto.UserFollowerStatsDto;
import com.socio.socio.entity.Group;
import com.socio.socio.entity.User;
import com.socio.socio.service.AdminService;
import com.socio.socio.service.GroupService;
import com.socio.socio.service.PostService;
import com.socio.socio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * AdminController provides administrative endpoints for managing users, posts, reports,
 * analytics, and group-related operations.
 *
 * All endpoints in this controller are secured and require the user to have an ADMIN role.
 *
 * Features include:
 * - Creating/updating admin users
 * - Viewing user follower statistics
 * - Accessing post analytics data
 * - Viewing reported posts grouped by criteria
 * - Sorting groups by member and post counts
 * - Moderating reported posts (approve/reject)
 * - Uploading users in bulk via CSV/XLSX
 *
 * This controller delegates the core logic to service layer components such as AdminService,
 * PostService, GroupService, and UserService.
 */

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final PostService postService;
    private final GroupService groupService;
    private final UserService userService;

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createOrUpdateAdmin(@RequestBody User user) {
        return ResponseEntity.ok(adminService.createOrUpdateAdmin(user));
    }

    @GetMapping("/user-stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserFollowerStatsDto>> getUserStats() {
        return ResponseEntity.ok(adminService.getUserStats());
    }

    @GetMapping("/analytics/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PostAnalyticsDto>> getPostAnalytics() {
        return ResponseEntity.ok(postService.getPostAnalytics());
    }

    @GetMapping("/reports/grouped")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getGroupedReportedPosts() {
        return ResponseEntity.ok(adminService.getGroupedReportedPosts());
    }

    @GetMapping("/groups/sorted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Group>> getGroupsSorted() {
        return ResponseEntity.ok(groupService.getGroupsSorted());
    }

    @PostMapping("/moderate/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> moderatePost(
            @PathVariable Long postId,
            @RequestParam String action
    ) {
        return ResponseEntity.ok(postService.moderateReportedPost(postId, action));
    }

    @PostMapping("/users/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadUsers(@RequestParam("file") MultipartFile file) {
        try {
            userService.uploadUsers(file);
            return ResponseEntity.ok("Users uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading users: " + e.getMessage());
        }
    }
}