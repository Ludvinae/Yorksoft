package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DirectorUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    // ---------- valid case ----------

    @Test
    public void directorWithValidData_shouldBeValid() {
        Director director = new Director();
        director.setFirstName("John");
        director.setLastName("Doe");
        director.setEmail("john.doe@test.com");
        director.setPhoneNumber("0123456789");
        director.setPassword("securePassword");

        boolean hasViolation = !validator.validate(director, OnCreate.class).isEmpty();

        Assertions.assertFalse(hasViolation, "Director should be valid");
    }
}
