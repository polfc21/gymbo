package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.ReviewEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.ReviewEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity reviewer = new UserEntityRepository().getReviewer();
    private final UserEntity reviewed = new UserEntityRepository().getReviewed();
    private final ReviewEntity review = new ReviewEntityRepository().getReview();

    @BeforeEach
    void setUp() {
        this.reviewer.setId(null);
        this.entityManager.persist(this.reviewer);
        this.reviewed.setId(null);
        this.entityManager.persist(this.reviewed);
        this.review.setReviewer(this.reviewer);
        this.review.setReviewed(this.reviewed);
        this.review.setId(null);
        this.entityManager.persist(this.review);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.review);
        this.entityManager.remove(this.reviewed);
        this.entityManager.remove(this.reviewer);
    }

    @Test
    void givenIdAndPlayerIsReviewerWhenFindByIdAndPlayerIsReviewerOrReviewedThenReturnReview() {
        ReviewEntity reviewFound = this.reviewRepository.findByIdAndPlayerIsReviewerOrReviewed(this.review.getId(), this.reviewer.getId());

        assertThat(reviewFound, is(this.review));
    }

    @Test
    void givenIdAndPlayerIsReviewedWhenFindByIdAndPlayerIsReviewerOrReviewedThenReturnReview() {
        ReviewEntity reviewFound = this.reviewRepository.findByIdAndPlayerIsReviewerOrReviewed(this.review.getId(), this.reviewed.getId());

        assertThat(reviewFound, is(this.review));
    }

    @Test
    void givenIdAndPlayerIsNotReviewerOrReviewedWhenFindByIdAndPlayerIsReviewerOrReviewedThenReturnNull() {
        ReviewEntity reviewFound = this.reviewRepository.findByIdAndPlayerIsReviewerOrReviewed(this.review.getId(), this.reviewed.getId() + 100);

        assertNull(reviewFound);
    }

    @Test
    void givenNonExistentIdAndPlayerIsReviewerWhenFindByIdAndPlayerIsReviewerOrReviewedThenReturnNull() {
        ReviewEntity reviewFound = this.reviewRepository.findByIdAndPlayerIsReviewerOrReviewed(this.review.getId() + 100, this.reviewer.getId());

        assertNull(reviewFound);
    }

    @Test
    void givenNonExistentIdAndPlayerIsReviewedWhenFindByIdAndPlayerIsReviewerOrReviewedThenReturnNull() {
        ReviewEntity reviewFound = this.reviewRepository.findByIdAndPlayerIsReviewerOrReviewed(this.review.getId() + 100, this.reviewed.getId());

        assertNull(reviewFound);
    }

    @Test
    void givenReviewedIdWhenFindAllByReviewedIdThenReturnReviews() {
        List<ReviewEntity> reviews = this.reviewRepository.findAllByReviewedId(this.reviewed.getId());

        assertThat(reviews, is(List.of(this.review)));
    }

}
