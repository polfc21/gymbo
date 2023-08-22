package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.ReviewEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ReviewEntityRepository {

    private final UserEntity reviewer = new UserEntityRepository().getReviewer();
    private final UserEntity reviewed = new UserEntityRepository().getReviewed();

    public ReviewEntity getReview() {
        return ReviewEntity.builder()
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
