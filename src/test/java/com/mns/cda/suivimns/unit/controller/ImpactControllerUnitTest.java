package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ImpactController;
import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.service.inter.iImpactService;
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

@WebMvcTest(controllers = ImpactController.class)
class ImpactControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iImpactService impactService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Impact impact;

    @BeforeEach
    void setUp() {
        impact = new Impact();
        impact.setIdImpact(1);

        // ⚠️ Adapter si @NotNull sur d'autres champs

    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(impactService.findAll()).thenReturn(List.of(impact));

        mockMvc.perform(get("/impact/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idImpact").value(1))
                .andExpect(jsonPath("$[0].content").value("Test content"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(impactService.findById(1)).thenReturn(Optional.of(impact));

        mockMvc.perform(get("/impact/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idImpact").value(1))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(impactService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/impact/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(impactService.save(any(Impact.class))).thenReturn(impact);

        mockMvc.perform(post("/impact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(impact)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(impactService.findById(1)).thenReturn(Optional.of(impact));

        mockMvc.perform(delete("/impact/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(impactService).delete(impact);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(impactService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/impact/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(impactService.update(any(Impact.class), eq(1))).thenReturn(impact);

        mockMvc.perform(put("/impact/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(impact)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(impactService.update(any(Impact.class), eq(1)))
                .thenThrow(new iImpactService.ImpactNotFoundException());

        mockMvc.perform(put("/impact/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(impact)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}