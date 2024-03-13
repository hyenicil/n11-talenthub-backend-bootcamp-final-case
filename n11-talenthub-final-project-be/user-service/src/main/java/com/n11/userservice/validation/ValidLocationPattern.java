package com.n11.userservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mehmet Akif Tanisik
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocationPatternValiadator.class)
public @interface ValidLocationPattern {
    String message() default "Location pattern should be (latitude,longitude).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class LocationPatternValiadator implements ConstraintValidator<ValidLocationPattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (value == null || value.isBlank()) {
            context.buildConstraintViolationWithTemplate("Location must not be null or blank.").addConstraintViolation();
            return false;
        }

        String[] coordinates = value.split(",");

        if (coordinates.length != 2) {
            context.buildConstraintViolationWithTemplate("Invalid location pattern.").addConstraintViolation();
            return false;
        }

        if (coordinates[0].contains(" ") || coordinates[1].contains(" ")){
            context.buildConstraintViolationWithTemplate("Location cant contain white space!").addConstraintViolation();
            return false;
        }

        try {
            double latitude = Double.parseDouble(coordinates[0].trim());
            double longitude = Double.parseDouble(coordinates[1].trim());
            context.buildConstraintViolationWithTemplate("Lat[-90, 90], Long[-180, 180]").addConstraintViolation();
            return (latitude >= -90 && latitude <= 90) && (longitude >= -180 && longitude <= 180);
        } catch (NumberFormatException e) {
            context.buildConstraintViolationWithTemplate("Invalid location latitude|longitude pattern.").addConstraintViolation();
            return false;
        }
    }
}

