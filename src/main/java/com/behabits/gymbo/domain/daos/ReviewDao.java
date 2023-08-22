package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Review;

public interface ReviewDao {
    Review saveReview(Review review);
    Review findReviewById(Long id);
}
