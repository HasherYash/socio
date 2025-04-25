package com.socio.socio.repository;

import com.socio.socio.entity.ReportedPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedPostRepository extends JpaRepository<ReportedPost, Long> {
}