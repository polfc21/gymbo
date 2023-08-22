package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.infrastructure.controller.dto.request.ReviewRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewApiMapper {

    private final UserApiMapper userApiMapper;

    public Review toDomain(ReviewRequest request) {
        Review domain = new Review();
        domain.setRating(request.getRating());
        domain.setComment(request.getComment());
        return domain;
    }

    public ReviewResponse toResponse(Review domain) {
        ReviewResponse response = new ReviewResponse();
        response.setId(domain.getId());
        response.setRating(domain.getRating());
        response.setComment(domain.getComment());
        response.setCreatedAt(domain.getCreatedAt());
        response.setUpdatedAt(domain.getUpdatedAt());
        response.setReviewer(this.userApiMapper.toResponse(domain.getReviewer()));
        response.setReviewed(this.userApiMapper.toResponse(domain.getReviewed()));
        return response;
    }

}
