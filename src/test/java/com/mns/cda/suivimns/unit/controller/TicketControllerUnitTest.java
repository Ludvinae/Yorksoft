package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.TicketController;
import com.mns.cda.suivimns.dto.flat.TicketCreation;
import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.flat.TicketResponse;
import com.mns.cda.suivimns.dto.flat.TicketUpdatedDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    private Ticket ticket;
    private TicketResponse ticketResponse;
    private TicketUpdatedDto ticketUpdated;
    private TicketCreation ticketCreation;
    private TicketFullWithLatest ticketFull;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setTitle("Test title");
        ticket.setDescription("test description");

        // ⚠️ Adapter si @NotNull sur d'autres champs
        Urgency urgency = new Urgency();
        urgency.setIdUrgency(3);
        ticket.setUrgency(urgency);

        Impact impact = new Impact();
        impact.setIdImpact(3);
        ticket.setImpact(impact);

        Client client = new Client();
        client.setIdAppUser(1);
        ticket.setClient(client);

        // DTO
        ticketResponse = new TicketResponse(
                1, "Test title", "Test description", LocalDateTime.now(), 3,
                "Test number", "Test type designation", "Test software name",
                "Test first name", "Test last name", "Test status", "Test theme");

        ticketUpdated = new TicketUpdatedDto(
                1, "Test title", "Test description", 3, null);

        ticketCreation = new TicketCreation(
                "Test title", "Test description", 1, 1, 1, 1,
                "Test theme designation", 1);

        ticketFull = new TicketFullWithLatest(
                1, "Test title", null, 3, "Test number",
                "Test type designation", "Test software name",
                "Test theme designation", "Test status designation", 2);

    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(ticketService.findAllDto()).thenReturn(List.of(ticketResponse));

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

        when(ticketService.findById(1)).thenReturn(Optional.of(ticket));
        when(ticketService.responseToDto(ticket)).thenReturn(ticketResponse);

        mockMvc.perform(get("/ticket/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTicket").value(1))
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.description").value("Test description"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(ticketService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/ticket/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(ticketService.createTicket(any(TicketCreation.class))).thenReturn(ticket);
        when(ticketService.responseToDto(ticket)).thenReturn(ticketResponse);

        mockMvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketCreation)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.description").value("Test description"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(ticketService.findById(1)).thenReturn(Optional.of(ticket));

        mockMvc.perform(delete("/ticket/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(ticketService).delete(ticket);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(ticketService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/ticket/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(ticketService.update(any(TicketUpdatedDto.class), eq(1))).thenReturn(ticketUpdated);

        mockMvc.perform(put("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.description").value("Test description"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(ticketService.update(any(TicketUpdatedDto.class), eq(1)))
                .thenThrow(new TicketService.TicketNotFoundException());

        mockMvc.perform(put("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}