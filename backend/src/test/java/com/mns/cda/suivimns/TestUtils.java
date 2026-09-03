package com.mns.cda.suivimns;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class TestUtils {

    public static boolean constraintViolationExists(
            Set<ConstraintViolation<Object>> constraintViolations,
            String fieldName,
            String annotationName
    ) {
        return constraintViolations.stream().anyMatch(constraint -> {
            String field = constraint.getPropertyPath().toString();
            String error = constraint.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();

            return (field.equals(fieldName) && error.equals(annotationName));
        });
    }
}
