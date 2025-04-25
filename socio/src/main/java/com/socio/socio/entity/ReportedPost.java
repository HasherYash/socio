package com.socio.socio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User reporter;

    @ManyToOne
    private Post reportedPost;

    private String reason;

    private LocalDateTime reportedAt;
}