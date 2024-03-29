package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.repositories.ReviewModelRepository;
import com.behabits.gymbo.infrastructure.repository.ReviewRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ReviewEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.ReviewEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.ReviewEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaReviewDaoTest {

    @InjectMocks
    private JpaReviewDao reviewDao;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewEntityMapper reviewEntityMapper;

    private final Review review = new ReviewModelRepository().getReview();
    private final ReviewEntity reviewEntity = new ReviewEntityRepository().getReview();

    @Test
    void givenReviewWhenSaveReviewThenReturnReview() {
        when(this.reviewEntityMapper.toEntity(this.review)).thenReturn(this.reviewEntity);
        when(this.reviewRepository.save(this.reviewEntity)).thenReturn(this.reviewEntity);
        when(this.reviewEntityMapper.toDomain(this.reviewEntity)).thenReturn(this.review);

        assertThat(this.reviewDao.saveReview(this.review), is(this.review));
    }

    @Test
    void givenExistentIdWhenFindReviewByIdThenReturnReview() {
        Long existentId = 1L;

        when(this.reviewRepository.findById(existentId)).thenReturn(Optional.of(this.reviewEntity));
        when(this.reviewEntityMapper.toDomain(this.reviewEntity)).thenReturn(this.review);

        assertThat(this.reviewDao.findReviewById(existentId), is(this.review));
    }

    @Test
    void givenNonExistentIdWhenFindReviewByIdThenReturnNull() {
        Long nonExistentId = 1L;

        when(this.reviewRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertNull(this.reviewDao.findReviewById(nonExistentId));
    }

    @Test
    void givenReviewedIdWhenFindAllReviewsByReviewedIdThenReturnListOfReviews() {
        Long reviewedId = 1L;

        when(this.reviewRepository.findAllByReviewedId(reviewedId)).thenReturn(List.of(this.reviewEntity));
        when(this.reviewEntityMapper.toDomain(this.reviewEntity)).thenReturn(this.review);

        assertThat(this.reviewDao.findAllReviewsByReviewedId(reviewedId), is(List.of(this.review)));
    }

    @Test
    void givenReviewerIdAndReviewedIdWhenFindReviewByReviewerIdAndReviewedIdThenReturnReview() {
        Long reviewerId = 1L;
        Long reviewedId = 2L;

        when(this.reviewRepository.findByReviewerIdAndReviewedId(reviewerId, reviewedId)).thenReturn(this.reviewEntity);
        when(this.reviewEntityMapper.toDomain(this.reviewEntity)).thenReturn(this.review);

        assertThat(this.reviewDao.findReviewByReviewerIdAndReviewedId(reviewerId, reviewedId), is(this.review));
    }

    @Test
    void givenNonExistentReviewerIdAndReviewedIdWhenFindReviewByReviewerIdAndReviewedIdThenReturnNull() {
        Long nonExistentReviewerId = 1L;
        Long nonExistentReviewedId = 2L;

        when(this.reviewRepository.findByReviewerIdAndReviewedId(nonExistentReviewerId, nonExistentReviewedId)).thenReturn(null);

        assertNull(this.reviewDao.findReviewByReviewerIdAndReviewedId(nonExistentReviewerId, nonExistentReviewedId));
    }

    @Test
    void givenReviewWhenDeleteReviewThenVerifyReviewRepositoryDeleteIsCalled() {
        this.reviewDao.deleteReview(this.review);

        verify(this.reviewRepository).deleteById(this.review.getId());
    }
}
