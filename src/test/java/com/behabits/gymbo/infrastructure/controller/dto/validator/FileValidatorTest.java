package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.infrastructure.controller.dto.request.FileRequest;
import com.behabits.gymbo.infrastructure.controller.repositories.request.FileRequestRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class FileValidatorTest {

    private Validator validator;

    private FileRequestRepository fileRequestRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.fileRequestRepository = new FileRequestRepository();
    }

    @Test
    void givenCorrectFileRequestWhenValidateThenViolationsSizeIs0() {
        FileRequest fileRequest = this.fileRequestRepository.getCorrectFileRequest();

        var violations = this.validator.validate(fileRequest);

        assertThat(violations.size(), is(0));
    }

    @Test
    void givenIncorrectFileRequestWhenValidateThenViolationsSizeIs1() {
        FileRequest fileRequest = this.fileRequestRepository.getIncorrectFileRequest();

        var violations = this.validator.validate(fileRequest);

        assertThat(violations.size(), is(1));
    }
}
