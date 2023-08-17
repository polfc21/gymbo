package com.behabits.gymbo.infrastructure.controller.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return this.validateFile(multipartFile);
    }

    private boolean validateFile(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes().length > 0;
        } catch (Exception e) {
            return false;
        }
    }

}
