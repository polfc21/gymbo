package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.SerieRequestRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class SerieRequestTest {

    private Validator validator;

    private SerieRequestRepository serieRequestRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.serieRequestRepository = new SerieRequestRepository();
    }

    @Test
    void givenCorrectSerieRequestWhenValidateThenViolationsSizeIs0() {
        SerieRequest serieRequest = this.serieRequestRepository.getCorrectSerieRequest();

        Set<ConstraintViolation<SerieRequest>> violations = this.validator.validate(serieRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenIncorrectSerieRequestWhenValidateThenViolationsSizeIs3() {
        SerieRequest serieRequest = this.serieRequestRepository.getIncorrectSerieRequest();

        Set<ConstraintViolation<SerieRequest>> violations = this.validator.validate(serieRequest);

        assertThat(violations.size(), is(3));
    }

    @Test
    void givenNullSerieRequestWhenValidateThenViolationsSizeIs3() {
        SerieRequest serieRequest = this.serieRequestRepository.getNullSerieRequest();

        Set<ConstraintViolation<SerieRequest>> violations = this.validator.validate(serieRequest);

        assertThat(violations.size(), is(3));
    }

}
