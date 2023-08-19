package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.infrastructure.controller.dto.request.LocationRequest;
import com.behabits.gymbo.infrastructure.controller.repositories.request.LocationRequestRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class GeometryValidatorTest {

    private Validator validator;

    private LocationRequestRepository locationRequestRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.locationRequestRepository = new LocationRequestRepository();
    }

    @Test
    void givenWKTLocationRequestWhenValidateThenViolationsSizeIs0() {
        LocationRequest locationRequest = this.locationRequestRepository.getWKTLocationRequest();

        var violations = this.validator.validate(locationRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenGeoJsonLocationRequestWhenValidateThenViolationsSizeIs0() {
        LocationRequest locationRequest = this.locationRequestRepository.getGeoJsonLocationRequest();

        var violations = this.validator.validate(locationRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenNonValidGeometryWhenValidateThenViolationsSizeIs1() {
        LocationRequest locationRequest = this.locationRequestRepository.getIncorrectLocationRequest();

        var violations = this.validator.validate(locationRequest);

        assertThat(violations.size(), is(1));
    }

}
