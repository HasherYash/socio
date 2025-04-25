package com.socio.socio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

/**
 * PostAnalyticsDto is a Data Transfer Object (DTO) used for providing analytics data about a post.
 * It contains various statistics related to a post, including like and comment counts, file type, and whether the post is in a group.
 *
 * Fields:
 * - postId: The unique identifier of the post.
 * - createdBy: The email or username of the user who created the post.
 * - createdAt: The date the post was created.
 * - fileType: The type of the file associated with the post (e.g., image, video).
 * - inGroup: A flag indicating whether the post was made in a group.
 * - likeCount: The number of likes the post has received.
 * - commentCount: The number of comments on the post.
 */

@Data
@AllArgsConstructor
public class PostAnalyticsDto {
    private Long postId;
    private String createdBy;
    private LocalDate createdAt;
    private String fileType;
    private boolean inGroup;
    private int likeCount;
    private int commentCount;
}