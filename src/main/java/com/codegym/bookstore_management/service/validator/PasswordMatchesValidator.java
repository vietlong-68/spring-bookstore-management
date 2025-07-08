package com.codegym.bookstore_management.service.validator;

import com.codegym.bookstore_management.model.dto.RegisterDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterDTO> {

    @Override
    public boolean isValid(RegisterDTO registerDTO, ConstraintValidatorContext context) {
        boolean isPasswordConfirmed = registerDTO.isPasswordConfirmed();
        if (isPasswordConfirmed) {
            return true;
        }
        return false;
    }
}
