package com.socio.socio.service;

import com.socio.socio.dto.FollowDto;

import java.util.List;

public interface FollowService {
    void followUser(String currentUsername, String targetUsername);
    void unfollowUser(String currentUsername, String targetUsername);
    List<FollowDto> getFollowers(String username);
    List<FollowDto> getFollowing(String username);
}