package com.socio.socio.repository;

import com.socio.socio.entity.Like;
import com.socio.socio.entity.Post;
import com.socio.socio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    long countByPost(Post post);
}