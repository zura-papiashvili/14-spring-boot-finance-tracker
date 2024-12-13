package com.example.finance_tracker.common.validators;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define a custom annotation for password validation
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class) // Use PasswordValidator for the validation
public @interface ValidPassword {

    // Default error message
    public String message() default "Invalid password. It must contain at least one uppercase letter, one digit, and one special character.";

    // Grouping for validation
    public Class<?>[] groups() default {};

    // define default payloads
    public Class<?>[] payload() default {};
}
