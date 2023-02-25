package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.builder.request.SerieRequestBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class SerieRequestTest {

    private Validator validator;

    private SerieRequestBuilder serieRequestBuilder;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.serieRequestBuilder = new SerieRequestBuilder();
    }

    @Test
    void givenCorrectSerieRequestWhenValidateThenViolationsSizeIs0() {
        SerieRequest serieRequest = this.serieRequestBuilder.buildCorrectSerieRequest();

        Set<ConstraintViolation<SerieRequest>> violations = this.validator.validate(serieRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenIncorrectSerieRequestWhenValidateThenViolationsSizeIs3() {
        SerieRequest serieRequest = this.serieRequestBuilder.buildIncorrectSerieRequest();

        Set<ConstraintViolation<SerieRequest>> violations = this.validator.validate(serieRequest);

        assertThat(violations.size(), is(3));
    }

    @Test
    void givenNullSerieRequestWhenValidateThenViolationsSizeIs3() {
        SerieRequest serieRequest = this.serieRequestBuilder.buildNullSerieRequest();

        Set<ConstraintViolation<SerieRequest>> violations = this.validator.validate(serieRequest);

        assertThat(violations.size(), is(3));
    }

}
