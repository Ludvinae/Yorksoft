package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.TechnicianController;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.service.inter.iTechnicianService;
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

@WebMvcTest(controllers = TechnicianController.class)
class TechnicianControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iTechnicianService technicianService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Technician technician;

    @BeforeEach
    void setUp() {
        technician = new Technician();
        technician.setIdAppUser(1);
        technician.setPassword("Test password");

        // ⚠️ Adapter si @NotNull sur d'autres champs

    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(technicianService.findAll()).thenReturn(List.of(technician));

        mockMvc.perform(get("/technician/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAppUser").value(1));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(technicianService.findById(1)).thenReturn(Optional.of(technician));

        mockMvc.perform(get("/technician/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAppUser").value(1));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(technicianService.findById(10)).thenReturn(Optional.empty());

        mockMvc.perform(get("/technician/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(technicianService.save(any(Technician.class))).thenReturn(technician);

        mockMvc.perform(post("/technician")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(technician)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(technicianService.findById(1)).thenReturn(Optional.of(technician));

        mockMvc.perform(delete("/technician/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(technicianService).delete(technician);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(technicianService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/technician/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}