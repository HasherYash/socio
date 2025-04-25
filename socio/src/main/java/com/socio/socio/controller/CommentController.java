package com.socio.socio.controller;

import com.socio.socio.dto.CommentRequestDto;
import com.socio.socio.dto.CommentResponseDto;
import com.socio.socio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CommentController manages the comment-related operations for the soCiO social network application.
 * It provides endpoints to add, retrieve, and delete comments for posts.
 * The controller interacts with the CommentService to handle business logic for these operations.
 *
 * Endpoints:
 * - POST /api/comments/{postId}: Adds a new comment to the specified post.
 * - GET /api/comments/{postId}: Retrieves all comments for the specified post.
 * - DELETE /api/comments/{commentId}: Deletes the specified comment.
 */

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto dto,
            Authentication auth
    ) {
        return ResponseEntity.ok(commentService.addComment(postId, auth.getName(), dto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, Authentication auth) {
        commentService.deleteComment(commentId, auth.getName());
        return ResponseEntity.ok("Comment deleted successfully");
    }
}