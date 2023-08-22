package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.ReviewRequestRepository;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ReviewRequestTest {

    private final ReviewRequestRepository reviewRequestRepository = new ReviewRequestRepository();

    @Test
    void givenSameReviewRequestWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        ReviewRequest reviewRequest = this.reviewRequestRepository.getReviewRequest();
        ReviewRequest reviewRequest2 = this.reviewRequestRepository.getReviewRequest();

        assertThat(reviewRequest.getComment(), is(reviewRequest2.getComment()));
        assertThat(reviewRequest.getRating(), is(reviewRequest2.getRating()));
    }
}
