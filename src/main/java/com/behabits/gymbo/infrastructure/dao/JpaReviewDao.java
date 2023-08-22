package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.ReviewDao;
import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.infrastructure.repository.ReviewRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ReviewEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.ReviewEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
