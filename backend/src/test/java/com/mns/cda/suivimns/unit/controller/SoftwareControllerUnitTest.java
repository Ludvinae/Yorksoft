package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.SoftwareController;
import com.mns.cda.suivimns.dto.entity.SoftwareDto;
import com.mns.cda.suivimns.exception.SoftwareNotFoundException;
import com.mns.cda.suivimns.service.entity.SoftwareService;
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

@WebMvcTest(controllers = SoftwareController.class)
class SoftwareControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoftwareService softwareService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private SoftwareDto softwareDto;

    @BeforeEach
    void setUp() {
        //DTO
        softwareDto = new SoftwareDto(
                1, "Test software", "", 1);
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateInvalid() throws Exception {

        SoftwareDto invalidDto = new SoftwareDto(null,"", null, null);

        mockMvc.perform(post("/software")
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

        when(softwareService.findAll()).thenReturn(List.of(softwareDto));

        mockMvc.perform(get("/software/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSoftware").value(1))
                .andExpect(jsonPath("$[0].name").value("Test software"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(softwareService.findById(1)).thenReturn(softwareDto);

        mockMvc.perform(get("/software/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSoftware").value(1))
                .andExpect(jsonPath("$.name").value("Test software"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(softwareService.findById(1))
                .thenThrow(new SoftwareNotFoundException());

        mockMvc.perform(get("/software/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {

        when(softwareService.save(any(SoftwareDto.class))).thenReturn(softwareDto);

        mockMvc.perform(post("/software")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test software"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {

        doNothing().when(softwareService).delete(1);

        mockMvc.perform(delete("/software/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(softwareService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new SoftwareNotFoundException())
                .when(softwareService).delete(1);

        mockMvc.perform(delete("/software/1")
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

        when(softwareService.update(eq(1), any(SoftwareDto.class))).thenReturn(softwareDto);

        mockMvc.perform(put("/software/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test software"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(softwareService.update(eq(1), any(SoftwareDto.class)))
                .thenThrow(new SoftwareNotFoundException());

        mockMvc.perform(put("/software/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}