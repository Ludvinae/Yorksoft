package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.SoftwareTypeController;
import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.service.SoftwareTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SoftwareTypeController.class)
class SoftwareTypeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoftwareTypeService softwareTypeService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private SoftwareType softwareType;

    @BeforeEach
    void setUp() {
        softwareType = new SoftwareType();
        softwareType.setIdSoftwareType(1);
        softwareType.setDesignation("Test designation");

        // ⚠️ Adapter si @NotNull sur d'autres champs
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(softwareTypeService.findAll()).thenReturn(List.of(softwareType));

        mockMvc.perform(get("/software-type/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSoftwareType").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(softwareTypeService.findById(1)).thenReturn(Optional.of(softwareType));

        mockMvc.perform(get("/software-type/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSoftwareType").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(softwareTypeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/software-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(softwareTypeService.save(any(SoftwareType.class))).thenReturn(softwareType);

        mockMvc.perform(post("/software-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareType)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(softwareTypeService.findById(1)).thenReturn(Optional.of(softwareType));

        mockMvc.perform(delete("/software-type/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(softwareTypeService).delete(softwareType);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(softwareTypeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/software-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(softwareTypeService.update(any(SoftwareType.class), eq(1))).thenReturn(softwareType);

        mockMvc.perform(put("/software-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareType)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(softwareTypeService.update(any(SoftwareType.class), eq(1)))
                .thenThrow(new SoftwareTypeService.SoftwareTypeNotFoundException());

        mockMvc.perform(put("/software-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareType)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}