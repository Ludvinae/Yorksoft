package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.SoftwareTypeController;
import com.mns.cda.suivimns.dto.SoftwareTypeDto;
import com.mns.cda.suivimns.service.SoftwareTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SoftwareTypeController.class)
class SoftwareTypeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoftwareTypeService softwareTypeService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private SoftwareTypeDto softwareTypeDto;

    @BeforeEach
    void setUp() {
        // DTO
        softwareTypeDto = new SoftwareTypeDto(
                1, "Test designation");
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        SoftwareTypeDto invalidDto = new SoftwareTypeDto(null,"");

        mockMvc.perform(post("/software-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(softwareTypeService.findAll()).thenReturn(List.of(softwareTypeDto));

        mockMvc.perform(get("/software-type/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSoftwareType").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(softwareTypeService.findById(1)).thenReturn(softwareTypeDto);

        mockMvc.perform(get("/software-type/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSoftwareType").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(softwareTypeService.findById(1))
                .thenThrow(new SoftwareTypeService.SoftwareTypeNotFoundException());

        mockMvc.perform(get("/software-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(softwareTypeService.save(any(SoftwareTypeDto.class))).thenReturn(softwareTypeDto);

        mockMvc.perform(post("/software-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareTypeDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSoftwareType").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(softwareTypeService).delete(1);

        mockMvc.perform(delete("/software-type/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(softwareTypeService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new SoftwareTypeService.SoftwareTypeNotFoundException())
                .when(softwareTypeService).delete(1);

        mockMvc.perform(delete("/software-type/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(softwareTypeService.update(eq(1), any(SoftwareTypeDto.class))).thenReturn(softwareTypeDto);

        mockMvc.perform(put("/software-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareTypeDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(softwareTypeService.update(eq(1), any(SoftwareTypeDto.class)))
                .thenThrow(new SoftwareTypeService.SoftwareTypeNotFoundException());

        mockMvc.perform(put("/software-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(softwareTypeDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}