package com.socio.socio.service;

import com.socio.socio.entity.*;
import com.socio.socio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public String toggleLike(Long postId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return likeRepository.findByUserAndPost(user, post).map(existingLike -> {
            likeRepository.delete(existingLike);
            return "Post unliked";
        }).orElseGet(() -> {
            Like like = Like.builder()
                    .user(user)
                    .post(post)
                    .likedAt(LocalDateTime.now())
                    .build();
            likeRepository.save(like);
            return "Post liked";
        });
    }
}