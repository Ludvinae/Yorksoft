package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.StatusController;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.service.inter.iStatusService;
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

@WebMvcTest(controllers = StatusController.class)
class StatusControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iStatusService statusService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Status status;

    @BeforeEach
    void setUp() {
        status = new Status();
        status.setIdStatus(1);
        status.setDesignation("Test designation");

        // ⚠️ Adapter si @NotNull sur d'autres champs
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(statusService.findAll()).thenReturn(List.of(status));

        mockMvc.perform(get("/status/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idStatus").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(statusService.findById(1)).thenReturn(Optional.of(status));

        mockMvc.perform(get("/status/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStatus").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(statusService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/status/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(statusService.save(any(Status.class))).thenReturn(status);

        mockMvc.perform(post("/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(status)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(statusService.findById(1)).thenReturn(Optional.of(status));

        mockMvc.perform(delete("/status/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(statusService).delete(status);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(statusService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/status/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(statusService.update(any(Status.class), eq(1))).thenReturn(status);

        mockMvc.perform(put("/status/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(status)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(statusService.update(any(Status.class), eq(1)))
                .thenThrow(new iStatusService.StatusNotFoundException());

        mockMvc.perform(put("/status/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(status)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}