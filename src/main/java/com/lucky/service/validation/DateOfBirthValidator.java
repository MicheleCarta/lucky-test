package com.lucky.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static java.util.Objects.isNull;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, LocalDate> {

    private static final int MIN_AGE = 18;

    @Override
    public void initialize(ValidDateOfBirth constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext constraintValidatorContext) {
        return isNull(dob) || isWithinAcceptedRange(dob, LocalDate.now(ZoneOffset.UTC));
    }

    private static boolean isWithinAcceptedRange(LocalDate dob, LocalDate today) {
        LocalDate latestDoB = today.minusYears(MIN_AGE).plusDays(1);
        return dob.isBefore(latestDoB);
    }
}
