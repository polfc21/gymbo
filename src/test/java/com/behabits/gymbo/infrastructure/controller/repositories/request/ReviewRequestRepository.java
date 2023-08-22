package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.ReviewRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReviewRequestRepository {

    public ReviewRequest getReviewRequest() {
        return ReviewRequest.builder()
                .comment("comment")
                .rating(5)
                .usernameReviewed("reviewed")
                .build();
    }

    public ReviewRequest incorrectReviewRequest() {
        return ReviewRequest.builder()
                .comment("comment")
                .rating(6)
                .usernameReviewed("reviewed")
                .build();
    }

}
