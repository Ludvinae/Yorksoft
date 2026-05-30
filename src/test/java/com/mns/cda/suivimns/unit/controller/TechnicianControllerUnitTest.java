package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.TechnicianController;
import com.mns.cda.suivimns.dto.entity.TechnicianDto;
import com.mns.cda.suivimns.exception.TechnicianNotFoundException;
import com.mns.cda.suivimns.service.entity.TechnicianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TechnicianController.class)
class TechnicianControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TechnicianService technicianService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private TechnicianDto technicianDto;

    @BeforeEach
    void setUp() {
        // DTO
        technicianDto = new TechnicianDto(
                1, "Test firstName", "Test lastName",
                "Test@email.com", "Test phoneNumber", (byte) 1);
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        TechnicianDto invalidDto = new TechnicianDto(null,"", null, "wrong email format",null, null);

        mockMvc.perform(post("/technician")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(technicianService.findAll()).thenReturn(List.of(technicianDto));

        mockMvc.perform(get("/technician/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAppUser").value(1))
                .andExpect(jsonPath("$[0].email").value("Test@email.com"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(technicianService.findById(1)).thenReturn(technicianDto);

        mockMvc.perform(get("/technician/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAppUser").value(1))
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(technicianService.findById(1))
                .thenThrow(new TechnicianNotFoundException());

        mockMvc.perform(get("/technician/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(technicianService.save(any(TechnicianDto.class))).thenReturn(technicianDto);

        mockMvc.perform(post("/technician")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(technicianDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAppUser").value(1))
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }
/*
    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(technicianService).delete(1);

        mockMvc.perform(delete("/technician/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(technicianService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new TechnicianService.TechnicianNotFoundException())
                .when(technicianService).delete(1);

        mockMvc.perform(delete("/technician/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(technicianService.update(eq(1), any(TechnicianDto.class))).thenReturn(technicianDto);

        mockMvc.perform(patch("/technician/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(technicianDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(technicianService.update(eq(1), any(TechnicianDto.class)))
                .thenThrow(new TechnicianService.TechnicianNotFoundException());

        mockMvc.perform(patch("/technician/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(technicianDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

 */

}