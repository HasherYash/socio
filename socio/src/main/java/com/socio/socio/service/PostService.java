package com.socio.socio.service;

import com.socio.socio.dto.*;
import com.socio.socio.entity.*;
import com.socio.socio.repository.GroupRepository;
import com.socio.socio.repository.PostRepository;
import com.socio.socio.repository.ReportedPostRepository;
import com.socio.socio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportedPostRepository reportedPostRepository;

    @Autowired
    private GroupRepository groupRepository;

    public Post createPost(PostRequest request) {
        User user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = Post.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return postRepository.findByUser(user);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public PostResponse sharePost(Long originalPostId, String username, SharePostRequestDto dto) {
        Post original = postRepository.findById(originalPostId)
                .orElseThrow(() -> new RuntimeException("Original post not found"));

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String sharedContent = "";
        if (dto.getAdditionalText() != null && !dto.getAdditionalText().isEmpty()) {
            sharedContent += dto.getAdditionalText() + "\n\n";
        }
        sharedContent += "Shared from @" + original.getUser().getEmail() + ":\n" + original.getContent();

        Post sharedPost = Post.builder()
                .content(sharedContent)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(sharedPost);

        return mapToDto(sharedPost);
    }

    public void reportPost(Long postId, String reporterUsername, ReportPostRequestDto dto) {
        User reporter = userRepository.findByEmail(reporterUsername)
                .orElseThrow(() -> new RuntimeException("Reporter not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        ReportedPost reported = ReportedPost.builder()
                .reporter(reporter)
                .reportedPost(post)
                .reason(dto.getReason())
                .reportedAt(LocalDateTime.now())
                .build();

        reportedPostRepository.save(reported);
    }

    public PostResponse createPostInGroup(PostResponse request, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getUsers().contains(user)) {
            throw new RuntimeException("User is not a member of this group");
        }

        Post post = Post.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .group(group)
                .user(user)
                .build();

        post = postRepository.save(post);
        return mapToDto(post);
    }

    private PostResponse mapToDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .authorEmail(post.getUser().getEmail())
                .groupId(post.getGroup() != null ? post.getGroup().getId() : null)
                .build();
    }

    public List<PostAnalyticsDto> getPostAnalytics() {
        List<Post> posts = postRepository.findAllWithLikesAndComments();

        return posts.stream().map(p -> new PostAnalyticsDto(
                p.getId(),
                p.getUser().getEmail(),
                p.getCreatedAt().toLocalDate(),
                p.getFileType(),
                p.getGroup() != null,
                p.getLikes().size(),
                p.getComments().size()
        )).sorted(Comparator
                .comparing(PostAnalyticsDto::getLikeCount).reversed()
                .thenComparing(PostAnalyticsDto::getCommentCount).reversed()
        ).collect(Collectors.toList());
    }

    public String moderateReportedPost(Long postId, String action) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.isReported()) throw new RuntimeException("Post is not reported.");

        post.setModerationStatus(action.toUpperCase()); // APPROVED or REJECTED
        postRepository.save(post);
        return "Post marked as " + action;
    }
}