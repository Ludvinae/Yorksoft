package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.VersionTypeController;
import com.mns.cda.suivimns.dto.entity.VersionTypeDto;
import com.mns.cda.suivimns.exception.VersionTypeNotFoundException;
import com.mns.cda.suivimns.service.entity.VersionTypeService;
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

@WebMvcTest(controllers = VersionTypeController.class)
class VersionTypeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockBean
    private VersionTypeService versionTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    private VersionTypeDto versionTypeDto;

    @BeforeEach
    void setUp() {
        //DTO
        versionTypeDto = new VersionTypeDto(
                1, "Test designation", "ABC", (byte) 1);

    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateInvalid() throws Exception {

        VersionTypeDto invalidDto = new VersionTypeDto(null,"", "ABC", (byte) 1);

        mockMvc.perform(post("/version-type")
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

        when(versionTypeService.findAll()).thenReturn(List.of(versionTypeDto));

        mockMvc.perform(get("/version-type/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idVersionType").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(versionTypeService.findById(1)).thenReturn(versionTypeDto);

        mockMvc.perform(get("/version-type/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVersionType").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(versionTypeService.findById(1))
                .thenThrow(new VersionTypeNotFoundException());

        mockMvc.perform(get("/version-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {

        when(versionTypeService.save(any(VersionTypeDto.class))).thenReturn(versionTypeDto);

        mockMvc.perform(post("/version-type")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionTypeDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idVersionType").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {

        doNothing().when(versionTypeService).delete(1);

        mockMvc.perform(delete("/version-type/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(versionTypeService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new VersionTypeNotFoundException())
                .when(versionTypeService).delete(1);

        mockMvc.perform(delete("/version-type/1")
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

        when(versionTypeService.update(eq(1), any(VersionTypeDto.class))).thenReturn(versionTypeDto);

        mockMvc.perform(put("/version-type/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionTypeDto)))
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

        when(versionTypeService.update(eq(1), any(VersionTypeDto.class)))
                .thenThrow(new VersionTypeNotFoundException());

        mockMvc.perform(put("/version-type/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionTypeDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}