package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.UrgencyController;
import com.mns.cda.suivimns.dto.entity.UrgencyDto;
import com.mns.cda.suivimns.exception.UrgencyNotFoundException;
import com.mns.cda.suivimns.service.entity.UrgencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@WebMvcTest(controllers = UrgencyController.class)
class UrgencyControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrgencyService urgencyService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private UrgencyDto urgencyDto;

    @BeforeEach
    void setUp() {
        // DTO
        urgencyDto = new UrgencyDto(
                1, "Test designation", (byte) 1, "Test description");
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateInvalid() throws Exception {

        UrgencyDto invalidDto = new UrgencyDto(null,"", (byte) 1, null);

        mockMvc.perform(post("/urgency")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAll() throws Exception {

        when(urgencyService.findAll()).thenReturn(List.of(urgencyDto));

        mockMvc.perform(get("/urgency/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUrgency").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(urgencyService.findById(1)).thenReturn(urgencyDto);

        mockMvc.perform(get("/urgency/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUrgency").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(urgencyService.findById(1))
                .thenThrow(new UrgencyNotFoundException());

        mockMvc.perform(get("/urgency/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {

        when(urgencyService.save(any(UrgencyDto.class))).thenReturn(urgencyDto);

        mockMvc.perform(post("/urgency")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urgencyDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUrgency").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {

        doNothing().when(urgencyService).delete(1);

        mockMvc.perform(delete("/urgency/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(urgencyService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new UrgencyNotFoundException())
                .when(urgencyService).delete(1);

        mockMvc.perform(delete("/urgency/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdate() throws Exception {

        when(urgencyService.update(eq(1), any(UrgencyDto.class))).thenReturn(urgencyDto);

        mockMvc.perform(put("/urgency/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urgencyDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(urgencyService.update(eq(1), any(UrgencyDto.class)))
                .thenThrow(new UrgencyNotFoundException());

        mockMvc.perform(put("/urgency/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urgencyDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}