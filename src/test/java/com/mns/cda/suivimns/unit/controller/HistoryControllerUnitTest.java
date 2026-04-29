package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.HistoryController;
import com.mns.cda.suivimns.dto.HistoryDto;
import com.mns.cda.suivimns.service.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HistoryController.class)
class HistoryControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private HistoryDto historyDto;

    @BeforeEach
    void setUp() {
        historyDto = new HistoryDto(
                1, LocalDateTime.now(), null, 1, 1, 1);
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(historyService.findAll()).thenReturn(List.of(historyDto));

        mockMvc.perform(get("/history/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idHistory").value(1));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(historyService.findById(1)).thenReturn(historyDto);

        mockMvc.perform(get("/history/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idHistory").value(1));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(historyService.findById(1))
                .thenThrow(new HistoryService.HistoryNotFoundException());

        mockMvc.perform(get("/history/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}