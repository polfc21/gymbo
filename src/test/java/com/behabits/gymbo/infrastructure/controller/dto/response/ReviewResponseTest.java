package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.ReviewResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ReviewResponseTest {

    private final ReviewResponseRepository reviewResponseRepository = new ReviewResponseRepository();

    @Test
    void givenSameReviewResponseWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        ReviewResponse reviewResponse = this.reviewResponseRepository.getReview();
        ReviewResponse reviewResponse2 = this.reviewResponseRepository.getReview();

        assertThat(reviewResponse, is(reviewResponse2));
        assertThat(reviewResponse.hashCode(), is(reviewResponse2.hashCode()));
        assertThat(reviewResponse.getId(), is(reviewResponse2.getId()));
        assertThat(reviewResponse.getCreatedAt(), is(reviewResponse2.getCreatedAt()));
        assertThat(reviewResponse.getUpdatedAt(), is(reviewResponse2.getUpdatedAt()));
        assertThat(reviewResponse.getRating(), is(reviewResponse2.getRating()));
        assertThat(reviewResponse.getComment(), is(reviewResponse2.getComment()));
        assertThat(reviewResponse.getReviewer().getId(), is(reviewResponse2.getReviewer().getId()));
        assertThat(reviewResponse.getReviewed().getId(), is(reviewResponse2.getReviewed().getId()));
    }
}
