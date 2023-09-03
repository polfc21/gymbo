package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void givenNonExistentIdWhenFindReviewByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.reviewRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.reviewDao.findReviewById(nonExistentId));
    }

    @Test
    void givenReviewedIdWhenFindAllReviewsByReviewedIdThenReturnListOfReviews() {
        Long reviewedId = 1L;

        when(this.reviewRepository.findAllByReviewedId(reviewedId)).thenReturn(List.of(this.reviewEntity));
        when(this.reviewEntityMapper.toDomain(this.reviewEntity)).thenReturn(this.review);

        assertThat(this.reviewDao.findAllReviewsByReviewedId(reviewedId), is(List.of(this.review)));
    }
}
