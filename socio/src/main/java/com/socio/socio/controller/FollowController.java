package com.socio.socio.controller;

import com.socio.socio.dto.FollowDto;
import com.socio.socio.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FollowController manages the user follow/unfollow operations for the soCiO social network application.
 * It provides endpoints to follow, unfollow, and retrieve followers or following users.
 * The controller interacts with the FollowService to handle the business logic for following actions.
 *
 * Endpoints:
 * - POST /api/follow/{username}: Follows a user by their username.
 * - DELETE /api/follow/{username}: Unfollows a user by their username.
 * - GET /api/follow/followers/{username}: Retrieves the list of followers for the specified user.
 * - GET /api/follow/following/{username}: Retrieves the list of users the specified user is following.
 */

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{username}")
    public ResponseEntity<String> followUser(@PathVariable String username, Authentication auth) {
        followService.followUser(auth.getName(), username);
        return ResponseEntity.ok("Followed user: " + username);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> unfollowUser(@PathVariable String username, Authentication auth) {
        followService.unfollowUser(auth.getName(), username);
        return ResponseEntity.ok("Unfollowed user: " + username);
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<List<FollowDto>> getFollowers(@PathVariable String username) {
        return ResponseEntity.ok(followService.getFollowers(username));
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<List<FollowDto>> getFollowing(@PathVariable String username) {
        return ResponseEntity.ok(followService.getFollowing(username));
    }
}