package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.ReviewDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.SameReviewedException;
import com.behabits.gymbo.domain.exceptions.SameReviewerException;
import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.ReviewService;
import com.behabits.gymbo.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;
    private final AuthorityService authorityService;
    private final UserService userService;

    @Override
    public Review createReview(Review review, String usernameReviewed) {
        User reviewed = this.userService.findUserByUsername(usernameReviewed);
        User reviewer = this.authorityService.getLoggedUser();
        if (reviewed.getUsername().equals(reviewer.getUsername())) {
            throw new SameReviewerException("You can't review yourself");
        }
        this.checkIfReviewAlreadyExists(reviewer, reviewed);
        review.setReviewed(reviewed);
        review.setReviewer(reviewer);
        review.setCreatedAt(LocalDateTime.now());
        return this.reviewDao.saveReview(review);
    }

    private void checkIfReviewAlreadyExists(User reviewer, User reviewed) {
        Review review = this.reviewDao.findReviewByReviewerIdAndReviewedId(reviewer.getId(), reviewed.getId());
        if (review != null) {
            throw new SameReviewedException("You already reviewed this user");
        }
    }

    @Override
    public Review findReviewById(Long id) {
        Review review = this.reviewDao.findReviewById(id);
        if (review == null) {
            throw new NotFoundException("Review not found");
        }
        this.authorityService.checkLoggedUserHasPermissions(review);
        return review;
    }

    @Override
    public List<Review> findAllReviewsByUsername(String username) {
        User user = this.userService.findUserByUsername(username);
        return this.reviewDao.findAllReviewsByReviewedId(user.getId());
    }

    @Override
    public Review updateReview(Long id, Review review) {
        Review reviewToUpdate = this.findReviewById(id);
        reviewToUpdate.setRating(review.getRating());
        reviewToUpdate.setComment(review.getComment());
        reviewToUpdate.setUpdatedAt(LocalDateTime.now());
        return this.reviewDao.saveReview(reviewToUpdate);
    }

}
