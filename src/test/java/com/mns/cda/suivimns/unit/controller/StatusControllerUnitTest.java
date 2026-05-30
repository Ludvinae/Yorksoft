package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.StatusController;
import com.mns.cda.suivimns.dto.entity.StatusDto;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.StatusNotFoundException;
import com.mns.cda.suivimns.service.entity.StatusService;
import com.mns.cda.suivimns.service.workflow.StatusTransition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatusController.class)
class StatusControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatusService statusService;

    @MockBean
    private StatusTransition transition;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private StatusDto statusDto;

    @BeforeEach
    void setUp() {
        statusDto = new StatusDto(
                1, "Test designation", StatusEnum.OPEN, (byte) 1);
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        StatusDto invalidDto = new StatusDto(null,"",null, null);

        mockMvc.perform(post("/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(statusService.findAll()).thenReturn(List.of(statusDto));

        mockMvc.perform(get("/status/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idStatus").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"))
                .andExpect(jsonPath("$[0].code").value(StatusEnum.OPEN.name()));
    }

    // =========================
    // GET POSSIBLE TRANSITIONS - CORRECT SET
    // =========================
    @Test
    void shouldReturnAllowedTransitions() throws Exception {

        Set<StatusEnum> transitions = Set.of(
                StatusEnum.ASSIGNED,
                StatusEnum.REJECTED
        );

        when(transition.getAllowedTransitions(StatusEnum.OPEN))
                .thenReturn(transitions);

        mockMvc.perform(get("/status/allowed-transitions/OPEN"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$").isArray());
    }


    // =========================
    // GET POSSIBLE TRANSITIONS - INVALID STATUS
    // =========================
    @Test
    void shouldReturnBadRequestForInvalidStatus() throws Exception {

        mockMvc.perform(get("/status/allowed-transitions/INVALID"))
                .andExpect(status().isBadRequest());
    }


    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(statusService.findById(1)).thenReturn(statusDto);

        mockMvc.perform(get("/status/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStatus").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.code").value(StatusEnum.OPEN.name()));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(statusService.findById(1))
                .thenThrow(new StatusNotFoundException());

        mockMvc.perform(get("/status/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(statusService.save(any(StatusDto.class))).thenReturn(statusDto);

        mockMvc.perform(post("/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.code").value(StatusEnum.OPEN.name()));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(statusService).delete(1);

        mockMvc.perform(delete("/status/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(statusService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new StatusNotFoundException())
                .when(statusService).delete(1);

        mockMvc.perform(delete("/status/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(statusService.update(eq(1), any(StatusDto.class))).thenReturn(statusDto);

        mockMvc.perform(put("/status/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.code").value(StatusEnum.OPEN.name()));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(statusService.update(eq(1), any(StatusDto.class)))
                .thenThrow(new StatusNotFoundException());

        mockMvc.perform(put("/status/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}