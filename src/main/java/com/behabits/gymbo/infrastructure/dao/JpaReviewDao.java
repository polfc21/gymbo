package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.ReviewDao;
import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.infrastructure.repository.ReviewRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ReviewEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.ReviewEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaReviewDao implements ReviewDao {

    private final ReviewEntityMapper mapper;
    private final ReviewRepository reviewRepository;

    @Override
    public Review saveReview(Review review) {
        ReviewEntity entityToSave = this.mapper.toEntity(review);
        ReviewEntity entitySaved = this.reviewRepository.save(entityToSave);
        return this.mapper.toDomain(entitySaved);
    }

    @Override
    public Review findReviewById(Long id) {
        ReviewEntity entity = this.reviewRepository.findById(id)
                .orElse(null);
        return entity != null ? this.mapper.toDomain(entity) : null;
    }

    @Override
    public List<Review> findAllReviewsByReviewedId(Long reviewedId) {
        return this.reviewRepository.findAllByReviewedId(reviewedId)
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public Review findReviewByReviewerIdAndReviewedId(Long reviewerId, Long reviewedId) {
        ReviewEntity entity = this.reviewRepository.findByReviewerIdAndReviewedId(reviewerId, reviewedId);
        return entity != null ? this.mapper.toDomain(entity) : null;
    }

    @Override
    public void deleteReview(Review review) {
        this.reviewRepository.deleteById(review.getId());
    }

}
