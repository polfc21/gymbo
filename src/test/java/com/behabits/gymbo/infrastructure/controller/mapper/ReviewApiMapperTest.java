package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.repositories.ReviewModelRepository;
import com.behabits.gymbo.infrastructure.controller.dto.request.ReviewRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ReviewResponse;
import com.behabits.gymbo.infrastructure.controller.repositories.request.ReviewRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ReviewApiMapperTest {

    @Autowired
    private ReviewApiMapper reviewApiMapper;

    @Test
    void givenReviewRequestWhenMapToDomainThenReturnReview() {
        ReviewRequest reviewRequest = new ReviewRequestRepository().getReviewRequest();

        Review review = this.reviewApiMapper.toDomain(reviewRequest);

        assertThat(review.getComment(), is(reviewRequest.getComment()));
        assertThat(review.getRating(), is(reviewRequest.getRating()));
    }

    @Test
    void givenReviewWhenMapToResponseThenReturnReviewResponse() {
        Review review = new ReviewModelRepository().getReview();

        ReviewResponse reviewRequest = this.reviewApiMapper.toResponse(review);

        assertThat(reviewRequest.getId(), is(review.getId()));
        assertThat(reviewRequest.getComment(), is(review.getComment()));
        assertThat(reviewRequest.getRating(), is(review.getRating()));
        assertThat(reviewRequest.getCreatedAt(), is(review.getCreatedAt()));
        assertThat(reviewRequest.getUpdatedAt(), is(review.getUpdatedAt()));
        assertThat(reviewRequest.getReviewer().getId(), is(review.getReviewer().getId()));
        assertThat(reviewRequest.getReviewed().getId(), is(review.getReviewed().getId()));
    }

}
