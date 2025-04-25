package com.socio.socio.controller;

import com.socio.socio.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * LikeController manages the like-related operations for the soCiO social network application.
 * It provides endpoints for toggling likes on posts.
 * The controller interacts with the LikeService to handle the business logic for liking/unliking posts.
 *
 * Endpoints:
 * - POST /api/likes/{postId}: Toggles the like status for a specified post.
 */

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> toggleLike(@PathVariable Long postId, Authentication authentication) {
        String result = likeService.toggleLike(postId, authentication.getName());
        return ResponseEntity.ok(result);
    }
}