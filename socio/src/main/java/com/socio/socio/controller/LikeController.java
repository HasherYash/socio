package com.socio.socio.controller;

import com.socio.socio.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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