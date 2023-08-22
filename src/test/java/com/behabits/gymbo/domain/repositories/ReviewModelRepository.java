package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.models.User;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ReviewModelRepository {

    private final User reviewer = new UserModelRepository().getReviewer();
    private final User reviewed = new UserModelRepository().getReviewed();

    public Review getReview() {
        return Review.builder()
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
