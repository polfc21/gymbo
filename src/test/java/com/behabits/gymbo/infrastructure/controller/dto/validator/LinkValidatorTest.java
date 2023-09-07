package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.infrastructure.controller.repositories.request.LinkRequestRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class LinkValidatorTest {

    private Validator validator;

    private LinkRequestRepository linkRequestRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.linkRequestRepository = new LinkRequestRepository();
    }

    @Test
    void givenIncorrectLinkWhenValidateThenViolationsSizeIs1() {
        var linkRequest = this.linkRequestRepository.getIncorrectLinkRequest();

        var violations = this.validator.validate(linkRequest);

        assertThat(violations.size(), is(1));
    }

    @Test
    void givenCorrectLinkWithExerciseWhenValidateThenViolationsSizeIs0() {
        var linkRequest = this.linkRequestRepository.getLinkWithExerciseRequest();

        var violations = this.validator.validate(linkRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenCorrectLinkWithUserWhenValidateThenViolationsSizeIs0() {
        var linkRequest = this.linkRequestRepository.getLinkWithUserRequest();

        var violations = this.validator.validate(linkRequest);

        assertThat(violations.size(), is(0));
    }
}
