package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.TicketController;
import com.mns.cda.suivimns.dto.details.TicketDetailDto;
import com.mns.cda.suivimns.dto.details.TicketDetailFullDto;
import com.mns.cda.suivimns.dto.details.TicketDetailKnowledge;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.workflow.TicketCreationDto;
import com.mns.cda.suivimns.dto.workflow.TicketDescriptionDto;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.enumerate.ThemeEnum;
import com.mns.cda.suivimns.exception.TicketNotFoundException;
import com.mns.cda.suivimns.model.Admin;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.TicketService;
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
    private TicketDetailFullDto ticketDetailFullDto;

    private AppUserDetails principal;

    @BeforeEach
    void setUp() {
        List<Integer> list = new ArrayList<>();
        // DTO
        ticketDto = new TicketDto(
                1, "Test title", "Test description", null,
                null, null, 0, 85, 85,
                ThemeEnum.BUG, StatusEnum.OPEN, 1, 1, 1, 1, list, list,
                list, list);

        TicketDetailDto ticketDetailDto = new TicketDetailDto(1, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null,
                null, null, null, null, null,
                null, null, null, null);
        TicketDetailKnowledge ticketDetailKnowledge = new TicketDetailKnowledge(1, "Test subject");

        ticketDetailFullDto = new TicketDetailFullDto(ticketDetailDto,
                List.of(), ticketDetailKnowledge, List.of(), List.of());

        Admin admin = new Admin();
        admin.setIdAppUser(1);
        admin.setPassword("password");
        admin.setEmail("email@test.com");
        admin.setFirstName("FirstName");
        admin.setLastName("LastName");
        admin.setPhoneNumber("123456789");
        principal = new AppUserDetails(admin);
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
                .thenThrow(new TicketNotFoundException());

        mockMvc.perform(get("/ticket/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(ticketService.save(any(TicketCreationDto.class), principal)).thenReturn(ticketDto);

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

        doThrow(new TicketNotFoundException())
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

        when(ticketService.update(eq(1), any(TicketDescriptionDto.class), principal)).thenReturn(ticketDetailFullDto);

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

        when(ticketService.update(eq(1), any(TicketDescriptionDto.class), principal))
                .thenThrow(new TicketNotFoundException());

        mockMvc.perform(put("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}