package com.socio.socio.service;

import com.socio.socio.dto.CommentRequestDto;
import com.socio.socio.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto addComment(Long postId, String username, CommentRequestDto dto);
    List<CommentResponseDto> getCommentsByPost(Long postId);
    void deleteComment(Long commentId, String username);
}