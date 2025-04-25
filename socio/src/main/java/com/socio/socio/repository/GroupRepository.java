package com.socio.socio.repository;

import com.socio.socio.entity.Group;
import com.socio.socio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    // Custom query to search groups by name or description
    @Query("SELECT g FROM Group g WHERE " +
            "LOWER(g.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(g.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Group> searchGroups(@Param("searchTerm") String searchTerm);

    @Query("SELECT g FROM Group g LEFT JOIN g.members m LEFT JOIN Post p ON p.group.id = g.id " +
            "GROUP BY g ORDER BY COUNT(DISTINCT m) DESC, COUNT(DISTINCT p) DESC")
    List<Group> getGroupsSortedByMembersAndPosts();

    List<Group> findByUsersContaining(User user);

    Page<Group> findAll(Pageable pageable);
}