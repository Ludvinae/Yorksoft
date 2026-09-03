package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ClientController;
import com.mns.cda.suivimns.dto.entity.ClientDto;
import com.mns.cda.suivimns.dto.search.ClientListDto;
import com.mns.cda.suivimns.dto.search.ClientSearchCriteria;
import com.mns.cda.suivimns.exception.ClientNotFoundException;
import com.mns.cda.suivimns.service.entity.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
class ClientControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;


    private ClientDto clientDto;

    private ClientListDto clientListDto;

    @BeforeEach
    void setUp() {
        // DTO
        clientDto = new ClientDto(
                1, "Test firstName", "Test lastName",
                "Test@email.com", "Test phoneNumber", (byte) 1);

        clientListDto = new ClientListDto(1, "Test firstName", "Test lastName",
                "Test@email.com", "Test phoneNumber", (byte) 1, List.of());
    }

    // =========================
    // PAGINATION
    // =========================
    @Test
    @WithMockUser(roles="ADMIN")
    void transmitCorrectPaginationCriteria_shouldReturnCorrectPage() throws Exception {

        mockMvc.perform(get("/client/list")
                .param("page", "0")
                .param("size", "20")
                .param("lastName", "Dupont"));

        verify(clientService).search(
                any(ClientSearchCriteria.class),
                any(Pageable.class));

    }


    // =========================
    // GET ALL
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAll() throws Exception {

        Page<ClientListDto> page =
                new PageImpl<>(List.of(clientListDto));

        when(clientService.search(
                any(ClientSearchCriteria.class),
                any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/client/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idAppUser").value(1))
                .andExpect(jsonPath("$.content[0].email").value("Test@email.com"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(clientService.findById(1)).thenReturn(clientDto);

        mockMvc.perform(get("/client/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAppUser").value(1))
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(clientService.findById(1))
                .thenThrow(new ClientNotFoundException());

        mockMvc.perform(get("/client/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

/*
    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(clientService).delete(1);

        mockMvc.perform(delete("/client/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(clientService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new ClientService.ClientNotFoundException())
                .when(clientService).delete(1);

        mockMvc.perform(delete("/client/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(clientService.update(eq(1), any(ClientDto.class))).thenReturn(clientDto);

        mockMvc.perform(patch("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(clientService.update(eq(1), any(ClientDto.class)))
                .thenThrow(new ClientService.ClientNotFoundException());

        mockMvc.perform(patch("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

 */
}