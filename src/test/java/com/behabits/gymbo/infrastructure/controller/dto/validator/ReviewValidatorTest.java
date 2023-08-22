package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.infrastructure.controller.repositories.request.ReviewRequestRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class ReviewValidatorTest {

    private Validator validator;

    private ReviewRequestRepository reviewRequestRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.reviewRequestRepository = new ReviewRequestRepository();
    }

    @Test
    void givenIncorrectRatingWhenValidateThenViolationsSizeIs1() {
        var reviewRequest = this.reviewRequestRepository.incorrectReviewRequest();

        var violations = this.validator.validate(reviewRequest);

        assertThat(violations.size(), is(1));
    }

    @Test
    void givenCorrectRatingWhenValidateThenViolationsSizeIs0() {
        var reviewRequest = this.reviewRequestRepository.getReviewRequest();

        var violations = this.validator.validate(reviewRequest);

        assertThat(violations.size(), is(0));
    }
}
