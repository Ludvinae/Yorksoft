package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.UrgencyController;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.service.UrgencyService;
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

@WebMvcTest(controllers = UrgencyController.class)
class UrgencyControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrgencyService urgencyService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Urgency urgency;

    @BeforeEach
    void setUp() {
        urgency = new Urgency();
        urgency.setIdUrgency(1);
        urgency.setDesignation("Test designation");
        urgency.setPriorityFactor((byte) 1);

        // ⚠️ Adapter si @NotNull sur d'autres champs

    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(urgencyService.findAll()).thenReturn(List.of(urgency));

        mockMvc.perform(get("/urgency/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUrgency").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"))
                .andExpect(jsonPath("$[0].priorityFactor").value(1));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(urgencyService.findById(1)).thenReturn(Optional.of(urgency));

        mockMvc.perform(get("/urgency/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUrgency").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.priorityFactor").value(1));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(urgencyService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/urgency/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(urgencyService.save(any(Urgency.class))).thenReturn(urgency);

        mockMvc.perform(post("/urgency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urgency)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.priorityFactor").value(1));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(urgencyService.findById(1)).thenReturn(Optional.of(urgency));

        mockMvc.perform(delete("/urgency/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(urgencyService).delete(urgency);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(urgencyService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/urgency/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(urgencyService.update(any(Urgency.class), eq(1))).thenReturn(urgency);

        mockMvc.perform(put("/urgency/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urgency)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.priorityFactor").value(1));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(urgencyService.update(any(Urgency.class), eq(1)))
                .thenThrow(new UrgencyService.UrgencyNotFoundException());

        mockMvc.perform(put("/urgency/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urgency)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}