package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.VersionTypeController;
import com.mns.cda.suivimns.dto.VersionTypeDto;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VersionTypeController.class)
class VersionTypeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockBean
    private iVersionTypeService versionTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    private VersionTypeDto versionTypeResponseDto;
    private VersionTypeCreateDto versionTypeCreateDto;

    @BeforeEach
    void setUp() {
        //DTO
        versionTypeResponseDto = new VersionTypeDto(
                1, "Test designation", (byte) 1);

        versionTypeCreateDto = new VersionTypeCreateDto(
                "Test designation", (byte) 1);
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        VersionTypeCreateDto invalidDto = new VersionTypeCreateDto("", (byte) 1);

        mockMvc.perform(post("/version-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(versionTypeService.findAll()).thenReturn(List.of(versionTypeResponseDto));

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
    void shouldReturnById() throws Exception {

        when(versionTypeService.findById(1)).thenReturn(versionTypeResponseDto);

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
    void shouldReturn404WhenNotFound() throws Exception {

        when(versionTypeService.findById(1))
                .thenThrow(new iVersionTypeService.VersionTypeNotFoundException());

        mockMvc.perform(get("/version-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(versionTypeService.save(any(VersionTypeCreateDto.class))).thenReturn(versionTypeResponseDto);

        mockMvc.perform(post("/version-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionTypeCreateDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idVersionType").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(versionTypeService).delete(1);

        mockMvc.perform(delete("/version-type/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(versionTypeService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new iVersionTypeService.VersionTypeNotFoundException())
                .when(versionTypeService).delete(1);

        mockMvc.perform(delete("/version-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(versionTypeService.update(eq(1), any(VersionTypeCreateDto.class))).thenReturn(versionTypeResponseDto);

        mockMvc.perform(put("/version-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionTypeCreateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(versionTypeService.update(eq(1), any(VersionTypeCreateDto.class)))
                .thenThrow(new iVersionTypeService.VersionTypeNotFoundException());

        mockMvc.perform(put("/version-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionTypeCreateDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}