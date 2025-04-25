package com.socio.socio.repository;

import com.socio.socio.dto.UserFollowerStatsDto;
import com.socio.socio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

        @Query("SELECT new com.socio.socio.dto.UserFollowerStatsDto(u.email, COUNT(f.id), u.createdAt) " +
        "FROM User u LEFT JOIN Follow f ON f.following.id = u.id " +
        "GROUP BY u.email, u.createdAt " +
        "ORDER BY u.createdAt DESC, COUNT(f.id) DESC")
    List<UserFollowerStatsDto> getUsersGroupedByDateAndSortedByFollowers();

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    List<User> findByEmailContaining(String email);


}