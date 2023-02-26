package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.ExerciseRequestRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class ExerciseRequestTest {

    private Validator validator;

    private ExerciseRequestRepository exerciseRequestRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.exerciseRequestRepository = new ExerciseRequestRepository();
    }

    @Test
    void givenCorrectExerciseRequestWhenValidateThenViolationsSizeIs0() {
        ExerciseRequest exerciseRequest = this.exerciseRequestRepository.getCorrectExerciseRequest();

        Set<ConstraintViolation<ExerciseRequest>> violations = this.validator.validate(exerciseRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenIncorrectExerciseRequestWhenValidateThenViolationsSizeIs1() {
        ExerciseRequest exerciseRequest = this.exerciseRequestRepository.getIncorrectExerciseRequest();

        Set<ConstraintViolation<ExerciseRequest>> violations = this.validator.validate(exerciseRequest);

        assertThat(violations.size(), is(1));
    }

    @Test
    void givenNullExerciseRequestWhenValidateThenViolationsSizeIs1() {
        ExerciseRequest exerciseRequest = this.exerciseRequestRepository.getNullExerciseRequest();

        Set<ConstraintViolation<ExerciseRequest>> violations = this.validator.validate(exerciseRequest);

        assertThat(violations.size(), is(1));
    }
}
