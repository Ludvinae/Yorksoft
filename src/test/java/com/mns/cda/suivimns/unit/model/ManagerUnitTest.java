package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ManagerUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    // ---------- valid case ----------

    @Test
    public void managerWithValidData_shouldBeValid() {
        Manager manager = new Manager();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john.doe@test.com");
        manager.setPhoneNumber("0123456789");
        manager.setPassword("securePassword");

        boolean hasViolation = !validator.validate(manager, OnCreate.class).isEmpty();

        Assertions.assertFalse(hasViolation, "Manager should be valid");
    }
}
