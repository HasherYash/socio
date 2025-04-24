package com.socio.user_service.service;

import com.socio.user_service.dto.UserProfileDto;
import com.socio.user_service.dto.UserResponse;
import com.socio.user_service.model.User;
import com.socio.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Get a user by their ID
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserResponse(user);
    }

    // Get all users
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    // Get the user profile by email
    public UserProfileDto getUserProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserProfileDto(user);
    }

    // Get the currently logged-in user's profile
    public UserProfileDto getUserProfileByPrincipal(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserProfileDto(user);
    }

    // Toggle the user's profile privacy
    public void togglePrivacy(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfilePrivate(!user.isProfilePrivate());
        userRepository.save(user);
    }

    // Follow another user
    public void followUser(String currentUserEmail, String emailToFollow) {
        User currentUser = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new RuntimeException("User not found"));
        User userToFollow = userRepository.findByEmail(emailToFollow).orElseThrow(() -> new RuntimeException("User to follow not found"));

        currentUser.getFollowing().add(userToFollow.getId());
        userRepository.save(currentUser);
    }

    // Unfollow a user
    public void unfollowUser(String currentUserEmail, String emailToUnfollow) {
        User currentUser = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new RuntimeException("User not found"));
        User userToUnfollow = userRepository.findByEmail(emailToUnfollow).orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        currentUser.getFollowing().remove(userToUnfollow.getId());
        userRepository.save(currentUser);
    }

    // Get the list of followers for the current user
    public Set<String> getFollowers(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return userRepository.findAll().stream()
                .filter(u -> u.getFollowing().contains(user.getId()))
                .map(User::getEmail)
                .collect(Collectors.toSet());
    }

    // Get the list of users the current user is following
    public Set<String> getFollowing(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowing().stream()
                .map(id -> userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")).getEmail())
                .collect(Collectors.toSet());
    }

    // Helper methods to map user to DTOs
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .profilePrivate(user.isProfilePrivate())
                .build();
    }

    private UserProfileDto mapToUserProfileDto(User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profilePrivate(user.isProfilePrivate())
                .following(user.getFollowing())
                .build();
    }
}