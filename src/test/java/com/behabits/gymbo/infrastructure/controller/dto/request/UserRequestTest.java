package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.UserRequestRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class UserRequestTest {

    private Validator validator;

    private UserRequestRepository userRequestRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.userRequestRepository = new UserRequestRepository();
    }

    @Test
    void givenCorrectUserRequestWhenValidateThenViolationsSizeIs0() {
        UserRequest userRequest = this.userRequestRepository.getCorrectUserRequest();

        Set<ConstraintViolation<UserRequest>> violations = this.validator.validate(userRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenIncorrectUserRequestWhenValidateThenViolationsSizeIs1() {
        UserRequest userRequest = this.userRequestRepository.getIncorrectUserRequest();

        Set<ConstraintViolation<UserRequest>> violations = this.validator.validate(userRequest);

        assertThat(violations.size(), is(UserRequest.class.getDeclaredFields().length));
    }

}
