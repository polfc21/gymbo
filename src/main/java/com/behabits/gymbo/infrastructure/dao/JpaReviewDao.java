package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.ReviewDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
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
        ReviewEntity entityToCreate = this.mapper.toEntity(review);
        ReviewEntity createdEntity = this.reviewRepository.save(entityToCreate);
        return this.mapper.toDomain(createdEntity);
    }

    @Override
    public Review findReviewById(Long id) {
        ReviewEntity entity = this.reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        return this.mapper.toDomain(entity);
    }

    @Override
    public List<Review> findAllReviewsByReviewedId(Long reviewedId) {
        return this.reviewRepository.findAllByReviewedId(reviewedId)
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }
}
