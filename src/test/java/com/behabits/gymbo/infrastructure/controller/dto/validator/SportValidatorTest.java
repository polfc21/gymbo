package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.domain.models.Sport;
import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class SportValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void givenIncorrectSportWhenValidateThenViolationsSizeIs1() {
        TrainingRequest trainingRequest = TrainingRequest.builder()
                .name("name")
                .trainingDate(LocalDateTime.now())
                .sport("incorrect sport")
                .build();

        Set<ConstraintViolation<TrainingRequest>> violations = this.validator.validate(trainingRequest);

        assertThat(violations.size(), is(1));
    }

    @Test
    void givenCorrectSportWhenValidateThenViolationsSizeIs0() {
        TrainingRequest trainingRequest = TrainingRequest.builder()
                .name("name")
                .trainingDate(LocalDateTime.now())
                .sport(Sport.FOOTBALL.name())
                .build();

        Set<ConstraintViolation<TrainingRequest>> violations = this.validator.validate(trainingRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenIncorrectSportsWhenValidateThenViolationsSizeIs1() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .username("name")
                .email("email@email.com")
                .password("password")
                .matchingPassword("password")
                .sports(Set.of("incorrect sport", "incorrect sport 2"))
                .build();

        Set<ConstraintViolation<UserRequest>> violations = this.validator.validate(userRequest);

        assertThat(violations.size(), is(1));
    }

    @Test
    void givenCorrectSportsWhenValidateThenViolationsSizeIs0() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .username("name")
                .password("password")
                .matchingPassword("password")
                .email("email@email.com")
                .sports(Set.of(Sport.FOOTBALL.name(), Sport.BASKETBALL.name()))
                .build();

        Set<ConstraintViolation<UserRequest>> violations = this.validator.validate(userRequest);

        assertThat(violations.size(), is(0));
    }
}
