package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.VersionController;
import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.dto.VersionTypeDto;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.service.VersionService;
import com.mns.cda.suivimns.service.VersionTypeService;
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

@WebMvcTest(controllers = VersionController.class)
class VersionControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VersionService versionService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private VersionDto versionDto;

    @BeforeEach
    void setUp() {
        //DTO
        versionDto = new VersionDto(
                1, "Test number", LocalDateTime.now(), 1, 1);
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        VersionDto invalidDto = new VersionDto(null,"", null, null, null);

        mockMvc.perform(post("/version")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(versionService.findAll()).thenReturn(List.of(versionDto));

        mockMvc.perform(get("/version/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idVersion").value(1))
                .andExpect(jsonPath("$[0].versionNumber").value("Test number"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(versionService.findById(1)).thenReturn(versionDto);

        mockMvc.perform(get("/version/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVersion").value(1))
                .andExpect(jsonPath("$.versionNumber").value("Test number"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(versionService.findById(1))
                .thenThrow(new VersionService.VersionNotFoundException());

        mockMvc.perform(get("/version/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(versionService.save(any(VersionDto.class))).thenReturn(versionDto);

        mockMvc.perform(post("/version")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.versionNumber").value("Test number"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(versionService).delete(1);

        mockMvc.perform(delete("/version/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(versionService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new VersionService.VersionNotFoundException())
                .when(versionService).delete(1);

        mockMvc.perform(delete("/version/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(versionService.update(eq(1), any(VersionDto.class))).thenReturn(versionDto);

        mockMvc.perform(put("/version/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.versionNumber").value("Test number"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(versionService.update(eq(1), any(VersionDto.class)))
                .thenThrow(new VersionService.VersionNotFoundException());

        mockMvc.perform(put("/version/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}