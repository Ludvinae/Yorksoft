package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class SoftwareUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validSoftwareWithBlankName_shouldNotBeValid() {
        Software software = new Software();
        software.setName("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(software, OnCreate.class),
                "name",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Software name should not be blank");
    }

    @Test
    public void validSoftwareWithTooLongName_shouldNotBeValid() {
        Software software = new Software();
        software.setName("lkdjsqkdjsqlkjdlksqjdlksqjdlksqjdlkjzoijdiqjdlkslkqcnkjsqnckjqnkzokdoapzkdpoakdpozakd" +
                "sqlkjdlksqjdksqjdlksqjdlkqjsdlksqjcjnwjcbnxwvcnb<vciuzahzoiud_iuydiuzayuidyzaiudhzaoidj_çç998" +
                "içç_u_udoijiazjdzajdksqndjsqnkjbsqdkjsqdjqhdikozakdmlkqdlsq,ckxw,cnwxncbzdqdsdqdsqdqsdsdsqdoàoà");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(software),
                "name",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Software name should be at maximum 127 characters long");
    }

}
