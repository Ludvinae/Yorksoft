package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.TicketController;
import com.mns.cda.suivimns.dto.details.TicketDetailDto;
import com.mns.cda.suivimns.dto.details.TicketDetailFullDto;
import com.mns.cda.suivimns.dto.details.TicketDetailKnowledge;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.search.TicketListDto;
import com.mns.cda.suivimns.dto.workflow.*;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.TicketNotFoundException;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.service.business.TicketDetailService;
import com.mns.cda.suivimns.service.entity.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private TicketDetailService ticketDetailService;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    private TicketDto ticketDto;
    private TicketDetailFullDto ticketDetailFullDto;
    private Theme theme;

    @BeforeEach
    void setUp() {
        List<Integer> list = new ArrayList<>();
        theme = new Theme();
        theme.setCode("BUG");
        // DTO
        ticketDto = new TicketDto(
                1, "Test title", "Test description", null,
                null, null, 0, 85, 85,
                1, StatusEnum.OPEN, 1, 1, 1, 1, list, list,
                list, list);

        TicketDetailDto ticketDetailDto = new TicketDetailDto(1, "Test title",
                50, 50, StatusEnum.OPEN, false, null,
                1, "Test Test", "test@test.com", "", (byte) 0,
                1, "BUG", "Bug", "Test software", 1, "1.0.0 r",
                LocalDateTime.now(), null, "Test description", null, null,
                null, null, null, null);
        TicketDetailKnowledge ticketDetailKnowledge = new TicketDetailKnowledge(1, "Test subject");

        ticketDetailFullDto = new TicketDetailFullDto(ticketDetailDto,
                List.of(), ticketDetailKnowledge, List.of(), List.of(StatusEnum.ASSIGNED));
    }

    // ===================
    // GET
    // ==================

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void validTicketId_shouldReturnTicketById() throws Exception {

        when(ticketService.findById(1)).thenReturn(ticketDto);

        mockMvc.perform(get("/ticket/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(ticketService).findById(1);
    }

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldReturn404WhenTicketNotFound() throws Exception {

        when(ticketService.findById(1))
                .thenThrow(new TicketNotFoundException());

        mockMvc.perform(get("/ticket/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldReturnTicketPage() throws Exception {

        TicketListDto dto = new TicketListDto(
                1, "Test title", "Test description", LocalDateTime.now(), LocalDateTime.now()
                , null, null, false, 0, 50
                , StatusEnum.OPEN, "Test theme designation", "firstname", "lastname",
                "Test software", "1.0.0", "r", null, null);

        Page<TicketListDto> page = new PageImpl<>(List.of(dto));

        when(ticketService.getAllPageable(any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/ticket/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));

        verify(ticketService)
                .getAllPageable(any(), any(), any());
    }


    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldReturnTicketDetails() throws Exception {

        when(ticketDetailService.getTicketDetails(eq(1), any()))
                .thenReturn(ticketDetailFullDto);

        mockMvc.perform(get("/ticket/1/detail"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(ticketDetailService).getTicketDetails(eq(1), any());
    }

    // ===================
    // CREATE
    // ==================

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldCreateTicket() throws Exception {

        TicketCreationDto creationDto = new TicketCreationDto("Test title", "Test description",
                1, 1, 1, 1, 1, 1);

        when(ticketService.save(any(TicketCreationDto.class), any()))
                .thenReturn(ticketDto);

        mockMvc.perform(post("/ticket")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(ticketService)
                .save(any(), any());
    }

    // ===================
    // DELETE
    // ==================

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteTicket() throws Exception {

        doNothing().when(ticketService).delete(1);

        mockMvc.perform(delete("/ticket/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(ticketService).delete(1);
    }

    // ===================
    // PATCH
    // ==================

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldUpdateTicketDescription() throws Exception {

        TicketDescriptionDto dto = new TicketDescriptionDto("new description", "new solution");

        when(ticketService.update(eq(1), any(), any())).thenReturn(ticketDetailFullDto);

        mockMvc.perform(patch("/ticket/1/description")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(ticketService).update(eq(1), any(), any());
    }

    // ===================
    // WORKFLOW
    // ==================

    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldAssignTicket() throws Exception {

        TicketAssignmentDto dto = new TicketAssignmentDto(1,2,"Assignation");

        when(ticketService.assignTicket(eq(1),any())).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket/1/assign")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(ticketService).assignTicket(eq(1), any());
    }

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldCloseTicket() throws Exception {

        StateChangeJustification dto =new StateChangeJustification("resolved");

        when(ticketService.closeTicket(eq(1),any(),any())).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket/1/close")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(ticketService).closeTicket(eq(1), any(), any());
    }

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldStartProgress() throws Exception {

        StateChangeJustification dto = new StateChangeJustification("start");

        when(ticketService.takeTicketInCharge(eq(1), any(), any())).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket/1/start-progress")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(ticketService).takeTicketInCharge(eq(1), any(), any());
    }

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldResumeProgress() throws Exception {

        StateChangeJustification dto = new StateChangeJustification("resume");

        when(ticketService.resumeTicket(eq(1), any(), any())).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket/1/resume-progress")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(ticketService).resumeTicket(eq(1), any(), any());
    }

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldSolveTicket() throws Exception {

        StateChangeJustification dto = new StateChangeJustification("solution");

        when(ticketService.solveTicket(eq(1), any(), any())).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket/1/solve")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(ticketService).solveTicket(eq(1), any(), any());
    }

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void shouldSetWaitingStatus() throws Exception {

        TicketWaitDto dto = new TicketWaitDto(1, StatusEnum.WAITING_CLIENT, "Details insuffisants");

        when(ticketService.setWaitingStatus(eq(1), any())).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket/1/wait")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(ticketService).setWaitingStatus(eq(1), any());
    }
}