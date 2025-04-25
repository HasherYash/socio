package com.socio.socio.controller;

import com.socio.socio.dto.PostRequest;
import com.socio.socio.dto.PostResponse;
import com.socio.socio.dto.ReportPostRequestDto;
import com.socio.socio.dto.SharePostRequestDto;
import com.socio.socio.entity.Post;
import com.socio.socio.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * PostController manages the post-related operations for the soCiO social network application.
 * It provides endpoints for creating, retrieving, deleting posts, sharing posts, reporting posts, and creating group posts.
 * The controller interacts with the PostService to handle the business logic for these operations.
 *
 * Endpoints:
 * - POST /api/posts: Creates a new post.
 * - GET /api/posts: Retrieves a paginated list of all posts.
 * - GET /api/posts/user: Retrieves posts by a specific user.
 * - DELETE /api/posts/{postId}: Deletes a specified post.
 * - POST /api/posts/share/{originalPostId}: Shares a specified post.
 * - POST /api/posts/posts/{postId}/report: Reports a specified post.
 * - POST /api/posts/group: Creates a post in a group.
 */

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
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(postService.getAllPosts(pageable));
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