package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.application.geometry.GeometryChecker;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GeometryValidator implements ConstraintValidator<Geometry, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        GeometryChecker geometryChecker = new GeometryChecker();
        return geometryChecker.isWKT(o) || geometryChecker.isGeoJson(o);
    }

}
