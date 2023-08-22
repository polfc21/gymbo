package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.repositories.ReviewModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ReviewEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.ReviewEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ReviewEntityMapperTest {

    @Autowired
    private ReviewEntityMapper mapper;

    private final ReviewEntity reviewEntity = new ReviewEntityRepository().getReview();
    private final Review review = new ReviewModelRepository().getReview();

    @Test
    void givenReviewWhenMapToEntityThenReturnReviewEntity() {
        ReviewEntity reviewEntity = this.mapper.toEntity(this.review);

        assertThat(reviewEntity.getId(), is(this.review.getId()));
        assertThat(reviewEntity.getComment(), is(this.review.getComment()));
        assertThat(reviewEntity.getRating(), is(this.review.getRating()));
        assertThat(reviewEntity.getCreatedAt(), is(this.review.getCreatedAt()));
        assertThat(reviewEntity.getUpdatedAt(), is(this.review.getUpdatedAt()));
        assertThat(reviewEntity.getReviewer().getId(), is(this.review.getReviewer().getId()));
        assertThat(reviewEntity.getReviewed().getId(), is(this.review.getReviewed().getId()));
    }

    @Test
    void givenReviewEntityWhenMapToDomainThenReturnReview() {
        Review review = this.mapper.toDomain(this.reviewEntity);

        assertThat(review.getId(), is(this.reviewEntity.getId()));
        assertThat(review.getComment(), is(this.reviewEntity.getComment()));
        assertThat(review.getRating(), is(this.reviewEntity.getRating()));
        assertThat(review.getCreatedAt(), is(this.reviewEntity.getCreatedAt()));
        assertThat(review.getUpdatedAt(), is(this.reviewEntity.getUpdatedAt()));
        assertThat(review.getReviewer().getId(), is(this.reviewEntity.getReviewer().getId()));
        assertThat(review.getReviewed().getId(), is(this.reviewEntity.getReviewed().getId()));
    }

}
