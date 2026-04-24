package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.SoftwareController;
import com.mns.cda.suivimns.dto.flat.SoftwareDto;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.service.inter.iSoftwareService;
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

@WebMvcTest(controllers = SoftwareController.class)
class SoftwareControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iSoftwareService softwareService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Software software;

    @BeforeEach
    void setUp() {
        software = new Software();
        software.setIdSoftware(1);
        software.setName("TestSoft");

        // ⚠️ Adapter si @NotNull sur d'autres champs

    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(softwareService.findAll()).thenReturn(List.of(software));

        mockMvc.perform(get("/software/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSoftware").value(1))
                .andExpect(jsonPath("$[0].name").value("TestSoft"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(softwareService.findById(1)).thenReturn(Optional.of(software));

        mockMvc.perform(get("/software/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSoftware").value(1))
                .andExpect(jsonPath("$.name").value("TestSoft"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(softwareService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/software/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        SoftwareDto softwareDto = new SoftwareDto("TestSoft","", null, null);

        when(softwareService.createSoftware(any(SoftwareDto.class))).thenReturn(software);

        mockMvc.perform(post("/software")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("TestSoft"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(softwareService.findById(1)).thenReturn(Optional.of(software));

        mockMvc.perform(delete("/software/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(softwareService).delete(software);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(softwareService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/software/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(softwareService.update(any(Software.class), eq(1))).thenReturn(software);

        mockMvc.perform(put("/software/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(software)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestSoft"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(softwareService.update(any(Software.class), eq(1)))
                .thenThrow(new iSoftwareService.SoftwareNotFoundException());

        mockMvc.perform(put("/software/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(software)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}