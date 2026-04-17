package com.mns.cda.suivimns.unit.controller;

import com.mns.cda.suivimns.controller.SoftwareController;
import com.mns.cda.suivimns.mock.service.MockSoftwareService;
import com.mns.cda.suivimns.model.Software;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class SoftwareControllerUnitTest {

    @Test
    public void getSoftwareByExistingId_shouldReturnCode200() {
        SoftwareController controller = new SoftwareController(new MockSoftwareService());
        ResponseEntity<Software> response = controller.getById(1);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getSoftwareByNotExistingId_shouldReturnCode404() {
        SoftwareController controller = new SoftwareController(new MockSoftwareService());
        ResponseEntity<Software> response = controller.getById(32);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
