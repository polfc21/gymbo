package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.infrastructure.repository.entity.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewEntityMapper {

    private final UserEntityMapper userEntityMapper;

    public Review toDomain(ReviewEntity entity) {
        Review domain = new Review();
        domain.setId(entity.getId());
        domain.setComment(entity.getComment());
        domain.setRating(entity.getRating());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setReviewer(this.userEntityMapper.toDomain(entity.getReviewer()));
        domain.setReviewed(this.userEntityMapper.toDomain(entity.getReviewed()));
        return domain;
    }

    public ReviewEntity toEntity(Review domain) {
        ReviewEntity entity = new ReviewEntity();
        entity.setId(domain.getId());
        entity.setComment(domain.getComment());
        entity.setRating(domain.getRating());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setReviewer(this.userEntityMapper.toEntity(domain.getReviewer()));
        entity.setReviewed(this.userEntityMapper.toEntity(domain.getReviewed()));
        return entity;
    }

}
