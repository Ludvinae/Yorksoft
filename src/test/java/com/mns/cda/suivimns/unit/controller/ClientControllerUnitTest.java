package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ClientController;
import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.service.inter.iClientService;
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

@WebMvcTest(controllers = ClientController.class)
class ClientControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iClientService clientService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;
    private ClientDto clientDto;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setIdAppUser(1);
        client.setPassword("Test password");

        // ⚠️ Adapter si @NotNull sur d'autres champs

        // DTO
        clientDto = new ClientDto(
                1, "Test first name", "Test last name", "Test email",
                "Test number", (byte) 1);

    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(clientService.findAll()).thenReturn(List.of(clientDto));

        mockMvc.perform(get("/client/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAppUser").value(1));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(clientService.findDtoById(1)).thenReturn(Optional.of(clientDto));

        mockMvc.perform(get("/client/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAppUser").value(1));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(clientService.findDtoById(10)).thenReturn(Optional.empty());

        mockMvc.perform(get("/client/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(clientService.save(any(Client.class))).thenReturn(client);
        when(clientService.toDto(client)).thenReturn(clientDto);

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(clientService.findById(1)).thenReturn(Optional.of(client));

        mockMvc.perform(delete("/client/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(clientService).delete(client);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(clientService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/client/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(clientService.update(any(Client.class), eq(1))).thenReturn(client);

        mockMvc.perform(patch("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(clientService.update(any(Client.class), eq(1)))
                .thenThrow(new iClientService.ClientNotFoundException());

        mockMvc.perform(patch("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}