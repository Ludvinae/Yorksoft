package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ClassificationController;
import com.mns.cda.suivimns.dto.entity.ClassificationDto;
import com.mns.cda.suivimns.exception.ClassificationNotFoundException;
import com.mns.cda.suivimns.service.entity.ClassificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClassificationController.class)
class ClassificationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassificationService classificationService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    private ClassificationDto classificationDto;

    @BeforeEach
    void setUp() {
        classificationDto = new ClassificationDto(
              1, 1,1, LocalDateTime.now());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAll() throws Exception {

        when(classificationService.findAll()).thenReturn(List.of(classificationDto));

        mockMvc.perform(get("/classification/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTicket").value(1))
                .andExpect(jsonPath("$[0].idTheme").value(1));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(classificationService.findById(1)).thenReturn(classificationDto);

        mockMvc.perform(get("/classification/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(classificationService.findById(1))
                .thenThrow(new ClassificationNotFoundException());

        mockMvc.perform(get("/classification/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}