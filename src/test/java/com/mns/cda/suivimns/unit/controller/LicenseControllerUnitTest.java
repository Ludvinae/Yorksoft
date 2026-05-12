package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.LicenseController;
import com.mns.cda.suivimns.dto.entity.LicenseDto;
import com.mns.cda.suivimns.service.entity.LicenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LicenseController.class)
class LicenseControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService licenseService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private LicenseDto licenseDto;

    @BeforeEach
    void setUp() {
        // DTO
        licenseDto = new LicenseDto(
                1, "Test number", LocalDate.now(), 1, 1);
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        LicenseDto invalidDto = new LicenseDto(null,"", null, null, null);

        mockMvc.perform(post("/license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(licenseService.findAll()).thenReturn(List.of(licenseDto));

        mockMvc.perform(get("/license/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idLicense").value(1))
                .andExpect(jsonPath("$[0].licenseNumber").value("Test number"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(licenseService.findById(1)).thenReturn(licenseDto);

        mockMvc.perform(get("/license/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLicense").value(1))
                .andExpect(jsonPath("$.licenseNumber").value("Test number"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(licenseService.findById(1))
                .thenThrow(new LicenseService.LicenseNotFoundException());

        mockMvc.perform(get("/license/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(licenseService.save(any(LicenseDto.class))).thenReturn(licenseDto);

        mockMvc.perform(post("/license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(licenseDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idLicense").value(1))
                .andExpect(jsonPath("$.licenseNumber").value("Test number"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(licenseService).delete(1);

        mockMvc.perform(delete("/license/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(licenseService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new LicenseService.LicenseNotFoundException())
                .when(licenseService).delete(1);

        mockMvc.perform(delete("/license/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(licenseService.update(eq(1), any(LicenseDto.class))).thenReturn(licenseDto);

        mockMvc.perform(put("/license/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(licenseDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseNumber").value("Test number"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(licenseService.update(eq(1), any(LicenseDto.class)))
                .thenThrow(new LicenseService.LicenseNotFoundException());

        mockMvc.perform(put("/license/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(licenseDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}