package com.behabits.gymbo.infrastructure.controller.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse reviewer;
    private UserResponse reviewed;
}
