package com.behabits.gymbo.infrastructure.controller.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LinkValidator implements ConstraintValidator<ValidLink, String> {

    @Override
    public boolean isValid(String request, ConstraintValidatorContext constraintValidatorContext) {
        return this.isValidEntity(request);
    }

    private boolean isValidEntity(String entity) {
        return entity.equals("EXERCISE") || entity.equals("USER") || entity.equals("TRAINING");
    }

}
