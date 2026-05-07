package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.TicketController;
import com.mns.cda.suivimns.dto.TicketDto;
import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.enumerate.PriorityEnum;
import com.mns.cda.suivimns.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TicketController.class)
class TicketControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private TicketDto ticketDto;
    private TicketFullWithLatest ticketFull;

    @BeforeEach
    void setUp() {
        List<Integer> list = new ArrayList<>();
        // DTO
        ticketDto = new TicketDto(
                1, "Test title", "Test description", null,
                null, null, 0, PriorityEnum.VERY_HIGH, PriorityEnum.VERY_HIGH,
                1, 1, 1, 1, list, list,
                list, list);

        ticketFull = new TicketFullWithLatest(
                1, "Test title", null, PriorityEnum.MEDIUM, "Test number",
                "Test type designation", "Test software name",
                "Test theme designation", "Test status designation", 2);

    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(ticketService.findAll()).thenReturn(List.of(ticketDto));

        mockMvc.perform(get("/ticket/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTicket").value(1))
                .andExpect(jsonPath("$[0].title").value("Test title"))
                .andExpect(jsonPath("$[0].description").value("Test description"));
    }

    // =========================
    // GET ALL FULL
    // =========================
    @Test
    void shouldReturnAllFull() throws Exception {

        when(ticketService.getAllTicketFullWithLatest()).thenReturn(List.of(ticketFull));

        mockMvc.perform(get("/ticket/list/full"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test title"));
    }

    // =========================
    // GET ALL BY TECHNICIAN ID - OK
    // =========================
    @Test
    void shouldReturnByTechnicianId() throws Exception {

        when(ticketService.getTicketFullWithLatestByTechnician(1)).thenReturn(List.of(ticketFull));

        mockMvc.perform(get("/ticket/list/technician/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test title"));
    }

    // =========================
    // GET ALL BY TECHNICIAN ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenTechnicianNotFound() throws Exception {

        when(ticketService.getTicketFullWithLatestByTechnician(10)).thenReturn(List.of());

        mockMvc.perform(get("/ticket/list/technician/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(ticketService.findById(1)).thenReturn(ticketDto);

        mockMvc.perform(get("/ticket/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTicket").value(1))
                .andExpect(jsonPath("$.title").value("Test title"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(ticketService.findById(1))
                .thenThrow(new TicketService.TicketNotFoundException());

        mockMvc.perform(get("/ticket/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(ticketService.save(any(TicketDto.class))).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test title"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(ticketService).delete(1);

        mockMvc.perform(delete("/ticket/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(ticketService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new TicketService.TicketNotFoundException())
                .when(ticketService).delete(1);

        mockMvc.perform(delete("/ticket/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(ticketService.update(eq(1), any(TicketDto.class))).thenReturn(ticketDto);

        mockMvc.perform(put("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test title"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(ticketService.update(eq(1), any(TicketDto.class)))
                .thenThrow(new TicketService.TicketNotFoundException());

        mockMvc.perform(put("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}