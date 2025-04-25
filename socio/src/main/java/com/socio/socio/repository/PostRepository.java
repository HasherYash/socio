package com.socio.socio.repository;

import com.socio.socio.entity.Post;
import com.socio.socio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.likes l LEFT JOIN FETCH p.comments c")
    List<Post> findAllWithLikesAndComments();

    @Query("SELECT p.reportedBy, p.createdAt, p.fileType, COUNT(p) " +
            "FROM Post p WHERE p.reported = true " +
            "GROUP BY p.reportedBy, p.createdAt, p.fileType")
    List<Object[]> getGroupedReportedPosts();

    Page<Post> findAll(Pageable pageable);
}