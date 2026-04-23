package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ClassificationController;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import com.mns.cda.suivimns.service.inter.iClassificationService;
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

@WebMvcTest(controllers = ClassificationController.class)
class ClassificationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iClassificationService classificationService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Classification classification;

    @BeforeEach
    void setUp() {
        classification = new Classification();
        classification.setId(new ClassificationKey(1, 2));
        classification.setTicket(new Ticket());
        classification.setTheme(new Theme());

        // ⚠️ Adapter si @NotNull sur d'autres champs
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(classificationService.findAll()).thenReturn(List.of(classification));

        mockMvc.perform(get("/classification/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.idTicket").value(1))
                .andExpect(jsonPath("$[0].id.idTheme").value(2));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(classificationService.findById(any(ClassificationKey.class))).thenReturn(Optional.of(classification));

        mockMvc.perform(get("/classification/1/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.idTicket").value(1))
                .andExpect(jsonPath("$[0].id.idTheme").value(2));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(classificationService.findById(new ClassificationKey(1, 2))).thenReturn(Optional.empty());

        mockMvc.perform(get("/classification/1/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(classificationService.save(any(Classification.class))).thenReturn(classification);

        mockMvc.perform(post("/classification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classification)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}