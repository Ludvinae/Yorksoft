package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.VersionTypeController;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;
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

@WebMvcTest(controllers = VersionTypeController.class)
class VersionTypeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iVersionTypeService versionTypeService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private VersionType versionType;

    @BeforeEach
    void setUp() {
        versionType = new VersionType();
        versionType.setIdVersionType(1);
        versionType.setDesignation("Test designation");

        // ⚠️ Adapter si @NotNull sur d'autres champs
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(versionTypeService.findAll()).thenReturn(List.of(versionType));

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

        when(versionTypeService.findById(1)).thenReturn(Optional.of(versionType));

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

        when(versionTypeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/version-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(versionTypeService.save(any(VersionType.class))).thenReturn(versionType);

        mockMvc.perform(post("/version-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionType)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(versionTypeService.findById(1)).thenReturn(Optional.of(versionType));

        mockMvc.perform(delete("/version-type/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(versionTypeService).delete(versionType);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(versionTypeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/version-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(versionTypeService.update(any(VersionType.class), eq(1))).thenReturn(versionType);

        mockMvc.perform(put("/version-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionType)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(versionTypeService.update(any(VersionType.class), eq(1)))
                .thenThrow(new iVersionTypeService.VersionTypeNotFoundException());

        mockMvc.perform(put("/version-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versionType)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}