package com.codegym.bookstore_management.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import com.codegym.bookstore_management.service.UserService;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean checkEmailExists = userService.checkEmailExists(email);
        if (checkEmailExists) {
            return false;
        }
        return true;
    }
}
