package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClientUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    // ---------- valid case ----------

    @Test
    public void clientWithValidData_shouldBeValid() {
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@test.com");
        client.setPhoneNumber("0123456789");
        client.setPassword("securePassword");
        client.setImportance((byte) 5);

        boolean hasViolation = !validator.validate(client, OnCreate.class).isEmpty();

        Assertions.assertFalse(hasViolation, "Client should be valid");
    }
}