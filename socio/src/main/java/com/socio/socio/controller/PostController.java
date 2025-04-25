package com.socio.socio.controller;

import com.socio.socio.dto.PostRequest;
import com.socio.socio.dto.PostResponse;
import com.socio.socio.dto.ReportPostRequestDto;
import com.socio.socio.dto.SharePostRequestDto;
import com.socio.socio.entity.Post;
import com.socio.socio.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Post createPost(@RequestBody PostRequest request) {
        return postService.createPost(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public List<Post> getPostsByUser(@RequestParam String email) {
        return postService.getPostsByUser(email);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @PostMapping("/share/{originalPostId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PostResponse> sharePost(
            @PathVariable Long originalPostId,
            @RequestBody SharePostRequestDto dto,
            Authentication auth
    ) {
        return ResponseEntity.ok(postService.sharePost(originalPostId, auth.getName(), dto));
    }

    @PostMapping("/posts/{postId}/report")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> reportPost(
            @PathVariable Long postId,
            @RequestBody ReportPostRequestDto dto,
            Authentication auth
    ) {
        postService.reportPost(postId, auth.getName(), dto);
        return ResponseEntity.ok("Post reported successfully.");
    }

    @PostMapping("/group")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PostResponse> createGroupPost(
            @RequestBody PostResponse dto,
            Authentication auth
    ) {
        return ResponseEntity.ok(postService.createPostInGroup(dto, auth.getName()));
    }

}