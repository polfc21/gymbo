package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.domain.models.Sport;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SportValidator implements ConstraintValidator<ValidSport, String> {
    @Override
    public boolean isValid(String request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }
        for (Sport sport : Sport.values()) {
            if (sport.name().equalsIgnoreCase(request)) {
                return true;
            }
        }
        return false;
    }

}

