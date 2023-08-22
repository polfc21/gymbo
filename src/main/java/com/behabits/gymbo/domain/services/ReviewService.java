package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Review;

public interface ReviewService {
    Review createReview(Review review, String usernameReviewed);
    Review findReviewById(Long id);
}
