package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.KnowledgeController;
import com.mns.cda.suivimns.dto.entity.KnowledgeDto;
import com.mns.cda.suivimns.exception.KnowledgeNotFoundException;
import com.mns.cda.suivimns.service.entity.KnowledgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = KnowledgeController.class)
class KnowledgeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KnowledgeService knowledgeService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private KnowledgeDto knowledgeDto;

    @BeforeEach
    void setUp() {
        // DTO
        List<Integer> versions = new ArrayList<>();
        versions.add(1);

        List<Integer> procedures = new ArrayList<>();
        procedures.add(1);

        knowledgeDto = new KnowledgeDto(
                1, "Test subject", 1, versions, procedures);

    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateInvalid() throws Exception {

        KnowledgeDto invalidDto = new KnowledgeDto(null,"", null, null, null);

        mockMvc.perform(post("/knowledge")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAll() throws Exception {

        when(knowledgeService.findAll()).thenReturn(List.of(knowledgeDto));

        mockMvc.perform(get("/knowledge/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idKnowledge").value(1))
                .andExpect(jsonPath("$[0].subject").value("Test subject"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(knowledgeService.findById(1)).thenReturn(knowledgeDto);

        mockMvc.perform(get("/knowledge/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idKnowledge").value(1))
                .andExpect(jsonPath("$.subject").value("Test subject"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(knowledgeService.findById(1))
                .thenThrow(new KnowledgeNotFoundException());

        mockMvc.perform(get("/knowledge/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {

        when(knowledgeService.save(any(KnowledgeDto.class))).thenReturn(knowledgeDto);

        mockMvc.perform(post("/knowledge")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knowledgeDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subject").value("Test subject"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {

        doNothing().when(knowledgeService).delete(1);

        mockMvc.perform(delete("/knowledge/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(knowledgeService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new KnowledgeNotFoundException())
                .when(knowledgeService).delete(1);

        mockMvc.perform(delete("/knowledge/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdate() throws Exception {

        when(knowledgeService.update(eq(1), any(KnowledgeDto.class))).thenReturn(knowledgeDto);

        mockMvc.perform(put("/knowledge/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knowledgeDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Test subject"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(knowledgeService.update(eq(1), any(KnowledgeDto.class)))
                .thenThrow(new KnowledgeNotFoundException());

        mockMvc.perform(put("/knowledge/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knowledgeDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}