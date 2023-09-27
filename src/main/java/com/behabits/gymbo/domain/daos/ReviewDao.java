package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Review;

import java.util.List;

public interface ReviewDao {
    Review saveReview(Review review);
    Review findReviewById(Long id);
    List<Review> findAllReviewsByReviewedId(Long reviewedId);
    Review findReviewByReviewerIdAndReviewedId(Long reviewerId, Long reviewedId);
}
