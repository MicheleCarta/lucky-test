package com.lucky.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DateOfBirthValidator.class)
public @interface ValidDateOfBirth {

    String message() default "must be 18 years old";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
