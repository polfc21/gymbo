package com.behabits.gymbo.infrastructure.controller.dto.validator;

import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object request, ConstraintValidatorContext constraintValidatorContext) {
        return this.passwordMatches((UserRequest) request);
    }

    public boolean passwordMatches(UserRequest request){
        return request.getPassword().equals(request.getMatchingPassword());
    }

}
