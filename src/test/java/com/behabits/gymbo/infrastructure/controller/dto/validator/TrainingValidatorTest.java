package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import com.behabits.gymbo.infrastructure.controller.repositories.request.TrainingRequestRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class TrainingValidatorTest {

    private Validator validator;

    private TrainingRequestRepository trainingRequestRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.trainingRequestRepository = new TrainingRequestRepository();
    }

    @Test
    void givenCorrectTrainingRequestWhenValidateThenViolationsSizeIs0() {
        TrainingRequest trainingRequest = this.trainingRequestRepository.getCorrectTrainingRequest();

        Set<ConstraintViolation<TrainingRequest>> violations = this.validator.validate(trainingRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenIncorrectTrainingRequestWhenValidateThenViolationsSizeIs2() {
        TrainingRequest trainingRequest = this.trainingRequestRepository.getIncorrectTrainingRequest();

        Set<ConstraintViolation<TrainingRequest>> violations = this.validator.validate(trainingRequest);

        assertThat(violations.size(), is(TrainingRequest.class.getDeclaredFields().length + 1));
    }

    @Test
    void givenNullTrainingRequestWhenValidateThenViolationsSizeIs2() {
        TrainingRequest trainingRequest = this.trainingRequestRepository.getNullTrainingRequest();

        Set<ConstraintViolation<TrainingRequest>> violations = this.validator.validate(trainingRequest);

        assertThat(violations.size(), is(TrainingRequest.class.getDeclaredFields().length - 1));
    }

}
