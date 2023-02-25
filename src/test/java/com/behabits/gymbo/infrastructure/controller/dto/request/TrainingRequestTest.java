package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.builder.request.TrainingRequestBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TrainingRequestTest {

    private Validator validator;

    private TrainingRequestBuilder trainingRequestBuilder;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.trainingRequestBuilder = new TrainingRequestBuilder();
    }

    @Test
    void givenCorrectTrainingRequestWhenValidateThenViolationsSizeIs0() {
        TrainingRequest trainingRequest = this.trainingRequestBuilder.buildCorrectTrainingRequest();

        Set<ConstraintViolation<TrainingRequest>> violations = this.validator.validate(trainingRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenIncorrectTrainingRequestWhenValidateThenViolationsSizeIs2() {
        TrainingRequest trainingRequest = this.trainingRequestBuilder.buildIncorrectTrainingRequest();

        Set<ConstraintViolation<TrainingRequest>> violations = this.validator.validate(trainingRequest);

        assertThat(violations.size(), is(2));
    }

    @Test
    void givenNullTrainingRequestWhenValidateThenViolationsSizeIs2() {
        TrainingRequest trainingRequest = this.trainingRequestBuilder.buildNullTrainingRequest();

        Set<ConstraintViolation<TrainingRequest>> violations = this.validator.validate(trainingRequest);

        assertThat(violations.size(), is(2));
    }
}
