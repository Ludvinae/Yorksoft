package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.LicenseController;
import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LicenseController.class)
class LicenseControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iLicenseService licenseService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private License license;

    @BeforeEach
    void setUp() {
        license = new License();
        license.setIdLicense(1);
        license.setLicenseNumber("Test number");
        license.setUserCount(10);

        // ⚠️ Adapter si @NotNull sur d'autres champs
        Software software = new Software();
        software.setIdSoftware(1);
        license.setSoftware(software);
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(licenseService.findAll()).thenReturn(List.of(license));

        mockMvc.perform(get("/license/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idLicense").value(1))
                .andExpect(jsonPath("$[0].licenseNumber").value("Test number"))
                .andExpect(jsonPath("$[0].userCount").value(10));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(licenseService.findById(1)).thenReturn(Optional.of(license));

        mockMvc.perform(get("/license/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLicense").value(1))
                .andExpect(jsonPath("$.licenseNumber").value("Test number"))
                .andExpect(jsonPath("$.userCount").value(10));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(licenseService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/license/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(licenseService.save(any(License.class))).thenReturn(license);

        mockMvc.perform(post("/license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(license)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.licenseNumber").value("Test number"))
                .andExpect(jsonPath("$.userCount").value(10));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(licenseService.findById(1)).thenReturn(Optional.of(license));

        mockMvc.perform(delete("/license/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(licenseService).delete(license);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(licenseService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/license/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(licenseService.update(any(License.class), eq(1))).thenReturn(license);

        mockMvc.perform(put("/license/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(license)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseNumber").value("Test number"))
                .andExpect(jsonPath("$.userCount").value(10));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(licenseService.update(any(License.class), eq(1)))
                .thenThrow(new iLicenseService.LicenseNotFoundException());

        mockMvc.perform(put("/license/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(license)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}