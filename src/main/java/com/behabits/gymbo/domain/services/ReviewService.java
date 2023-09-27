package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review, String usernameReviewed);
    Review findReviewById(Long id);
    List<Review> findAllReviewsByUsername(String username);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
}
