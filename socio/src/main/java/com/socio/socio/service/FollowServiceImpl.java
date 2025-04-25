package com.socio.socio.service;

import com.socio.socio.dto.FollowDto;
import com.socio.socio.entity.Follow;
import com.socio.socio.entity.User;
import com.socio.socio.repository.FollowRepository;
import com.socio.socio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing user follow/unfollow actions.
 * Provides functionality for following and unfollowing users, retrieving followers, and getting users that a user is following.
 *
 * Methods:
 * - followUser: Follows a target user.
 * - unfollowUser: Unfollows a target user.
 * - getFollowers: Retrieves a list of users following a specific user.
 * - getFollowing: Retrieves a list of users a specific user is following.
 */

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public void followUser(String currentUsername, String targetUsername) {
        if (currentUsername.equals(targetUsername)) {
            throw new RuntimeException("You cannot follow yourself");
        }

        User follower = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findByEmail(targetUsername)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        boolean alreadyFollowing = followRepository.findByFollowerAndFollowing(follower, following).isPresent();
        if (alreadyFollowing) {
            throw new RuntimeException("Already following this user");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();

        followRepository.save(follow);
    }

    @Override
    public void unfollowUser(String currentUsername, String targetUsername) {
        User follower = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findByEmail(targetUsername)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("Not following this user"));

        followRepository.delete(follow);
    }

    @Override
    public List<FollowDto> getFollowers(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.findByFollowing(user).stream()
                .map(f -> FollowDto.builder().username(f.getFollower().getEmail()).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowDto> getFollowing(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return followRepository.findByFollower(user).stream()
                .map(f -> FollowDto.builder().username(f.getFollowing().getEmail()).build())
                .collect(Collectors.toList());
    }
}