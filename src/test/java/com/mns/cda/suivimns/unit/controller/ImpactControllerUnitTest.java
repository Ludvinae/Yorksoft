package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ImpactController;
import com.mns.cda.suivimns.dto.entity.ImpactDto;
import com.mns.cda.suivimns.exception.ImpactNotFoundException;
import com.mns.cda.suivimns.service.entity.ImpactService;
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

@WebMvcTest(controllers = ImpactController.class)
class ImpactControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImpactService impactService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private ImpactDto impactDto;

    @BeforeEach
    void setUp() {
        // DTO
        impactDto = new ImpactDto(
                1, "Test designation", (byte) 1, "Test description");
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateInvalid() throws Exception {

        ImpactDto invalidDto = new ImpactDto(null,"", (byte) 1, null);

        mockMvc.perform(post("/impact")
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

        when(impactService.findAll()).thenReturn(List.of(impactDto));

        mockMvc.perform(get("/impact/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idImpact").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(impactService.findById(1)).thenReturn(impactDto);

        mockMvc.perform(get("/impact/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idImpact").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(impactService.findById(1))
                .thenThrow(new ImpactNotFoundException());

        mockMvc.perform(get("/impact/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {

        when(impactService.save(any(ImpactDto.class))).thenReturn(impactDto);

        mockMvc.perform(post("/impact")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(impactDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idImpact").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {

        doNothing().when(impactService).delete(1);

        mockMvc.perform(delete("/impact/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(impactService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new ImpactNotFoundException())
                .when(impactService).delete(1);

        mockMvc.perform(delete("/impact/1")
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

        when(impactService.update(eq(1), any(ImpactDto.class))).thenReturn(impactDto);

        mockMvc.perform(put("/impact/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(impactDto)))
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

        when(impactService.update(eq(1), any(ImpactDto.class)))
                .thenThrow(new ImpactNotFoundException());

        mockMvc.perform(put("/impact/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(impactDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}