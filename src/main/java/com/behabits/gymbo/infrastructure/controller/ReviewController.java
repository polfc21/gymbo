package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.services.ReviewService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.ReviewRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ReviewResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.ReviewApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.REVIEWS)
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewApiMapper mapper;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody @Valid ReviewRequest request) {
        Review reviewToCreate = this.mapper.toDomain(request);
        Review reviewCreated = this.reviewService.createReview(reviewToCreate, request.getUsernameReviewed());
        return new ResponseEntity<>(this.mapper.toResponse(reviewCreated), HttpStatus.CREATED);
    }

    @GetMapping(ApiConstant.ID)
    public ResponseEntity<ReviewResponse> findReviewById(@PathVariable Long id) {
        Review review = this.reviewService.findReviewById(id);
        return new ResponseEntity<>(this.mapper.toResponse(review), HttpStatus.OK);
    }

    @GetMapping(ApiConstant.REVIEWED + ApiConstant.USERNAME)
    public ResponseEntity<List<ReviewResponse>> findAllReviewsByUsername(@PathVariable String username) {
        List<ReviewResponse> reviews = this.reviewService.findAllReviewsByUsername(username)
                .stream()
                .map(this.mapper::toResponse)
                .toList();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping(ApiConstant.ID)
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody @Valid ReviewRequest request) {
        Review reviewToUpdate = this.mapper.toDomain(request);
        Review reviewUpdated = this.reviewService.updateReview(id, reviewToUpdate);
        return new ResponseEntity<>(this.mapper.toResponse(reviewUpdated), HttpStatus.OK);
    }

}
