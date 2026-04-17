package com.cloud.publishing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements
        ConstraintValidator<PasswordMatches, PasswordMatchable> {
    private String matchMessage;
    private String emptyMessage;
    private boolean required;
    private static final String PASSWORD_FIELD = "password";
    private static final String CONFIRM_FIELD = "passwordConfirm";

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        this.matchMessage = constraintAnnotation.message();
        this.emptyMessage = constraintAnnotation.emptyMessage();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(PasswordMatchable request, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String password = request.password();
        String confirm = request.passwordConfirm();
        boolean passwordEmpty = (password == null || password.isBlank());
        boolean confirmEmpty = (confirm == null || confirm.isBlank());
        if (passwordEmpty && confirmEmpty) {
            if (required) {
                addViolation(context, emptyMessage, PASSWORD_FIELD);
                return false;
            }
            return true;
        }
        if (passwordEmpty) {
            addViolation(context, emptyMessage, PASSWORD_FIELD);
            return false;
        }
        if (confirmEmpty) {
            addViolation(context, emptyMessage, CONFIRM_FIELD);
            return false;
        }
        if (!password.equals(confirm)) {
            addViolation(context, matchMessage, CONFIRM_FIELD);
            return false;
        }
        return true;
    }

    private void addViolation(
            ConstraintValidatorContext context,
            String message,
            String propertyName
    ) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propertyName)
                .addConstraintViolation();
    }
}