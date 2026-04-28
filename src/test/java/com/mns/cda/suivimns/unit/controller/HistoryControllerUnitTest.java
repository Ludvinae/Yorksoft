package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.HistoryController;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private History history;

    @BeforeEach
    void setUp() {
        history = new History();
        history.setIdHistory(1);

        // ⚠️ Adapter si @NotNull sur d'autres champs
        Ticket ticket = new Ticket();
        ticket.setIdTicket(1);
        history.setTicket(ticket);

        Status status = new Status();
        status.setIdStatus(1);
        history.setStatus(status);
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(historyService.findAll()).thenReturn(List.of(history));

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

        when(historyService.findById(1)).thenReturn(Optional.of(history));

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

        when(historyService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/history/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}