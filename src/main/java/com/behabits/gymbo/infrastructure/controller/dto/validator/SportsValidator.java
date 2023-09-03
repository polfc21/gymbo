package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.domain.models.Sport;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class SportsValidator implements ConstraintValidator<ValidSports, Set<String>> {
    @Override
    public boolean isValid(Set<String> sports, ConstraintValidatorContext context) {
        for (String sport : sports) {
            if (!this.isValidSport(sport)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidSport(String sport) {
        for (Sport enumSport : Sport.values()) {
            if (enumSport.name().equalsIgnoreCase(sport)) {
                return true;
            }
        }
        return false;
    }
}
