package com.socio.user_service.controller;

import com.socio.user_service.dto.UserProfileDto;
import com.socio.user_service.dto.UserResponse;
import com.socio.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserProfile(email));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile(Principal principal) {
        return ResponseEntity.ok(userService.getUserProfile(principal.getName()));
    }

    @PutMapping("/me/privacy")
    public ResponseEntity<String> togglePrivacy(Principal principal) {
        userService.togglePrivacy(principal.getName());
        return ResponseEntity.ok("Privacy setting updated");
    }

    @PostMapping("/follow/{email}")
    public ResponseEntity<String> followUser(@PathVariable String email, Principal principal) {
        userService.followUser(principal.getName(), email);
        return ResponseEntity.ok("Followed " + email);
    }

    @PostMapping("/unfollow/{email}")
    public ResponseEntity<String> unfollowUser(@PathVariable String email, Principal principal) {
        userService.unfollowUser(principal.getName(), email);
        return ResponseEntity.ok("Unfollowed " + email);
    }

    @GetMapping("/followers")
    public ResponseEntity<Set<String>> getFollowers(Principal principal) {
        return ResponseEntity.ok(userService.getFollowers(principal.getName()));
    }

    @GetMapping("/following")
    public ResponseEntity<Set<String>> getFollowing(Principal principal) {
        return ResponseEntity.ok(userService.getFollowing(principal.getName()));
    }
}