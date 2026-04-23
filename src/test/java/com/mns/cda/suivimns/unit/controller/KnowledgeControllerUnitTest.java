package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.KnowledgeController;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.service.inter.iKnowledgeService;
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

@WebMvcTest(controllers = KnowledgeController.class)
class KnowledgeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iKnowledgeService knowledgeService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Knowledge knowledge;

    @BeforeEach
    void setUp() {
        knowledge = new Knowledge();
        knowledge.setIdKnowledge(1);
        knowledge.setSubject("Test subject");

        // ⚠️ Adapter si @NotNull sur d'autres champs
        Theme theme = new Theme();
        theme.setIdTheme(1);
        knowledge.setTheme(theme);
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(knowledgeService.findAll()).thenReturn(List.of(knowledge));

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
    void shouldReturnById() throws Exception {

        when(knowledgeService.findById(1)).thenReturn(Optional.of(knowledge));

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
    void shouldReturn404WhenNotFound() throws Exception {

        when(knowledgeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/knowledge/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(knowledgeService.save(any(Knowledge.class))).thenReturn(knowledge);

        mockMvc.perform(post("/knowledge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knowledge)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subject").value("Test subject"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(knowledgeService.findById(1)).thenReturn(Optional.of(knowledge));

        mockMvc.perform(delete("/knowledge/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(knowledgeService).delete(knowledge);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(knowledgeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/knowledge/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(knowledgeService.update(any(Knowledge.class), eq(1))).thenReturn(knowledge);

        mockMvc.perform(put("/knowledge/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knowledge)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Test subject"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(knowledgeService.update(any(Knowledge.class), eq(1)))
                .thenThrow(new iKnowledgeService.KnowledgeNotFoundException());

        mockMvc.perform(put("/knowledge/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(knowledge)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}