package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.ReviewResponse;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ReviewResponseRepository {

    private final UserResponse reviewer = new UserResponseRepository().getReviewer();
    private final UserResponse reviewed = new UserResponseRepository().getReviewed();

    public ReviewResponse getReview() {
        return ReviewResponse.builder()
                .id(1L)
                .comment("comment")
                .rating(5)
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .reviewer(this.reviewer)
                .reviewed(this.reviewed)
                .build();
    }

}
