package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.DirectorController;
import com.mns.cda.suivimns.dto.entity.DirectorDto;
import com.mns.cda.suivimns.exception.DirectorNotFoundException;
import com.mns.cda.suivimns.service.entity.DirectorService;
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

@WebMvcTest(controllers = DirectorController.class)
class DirectorControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private DirectorDto directorDto;

    @BeforeEach
    void setUp() {
        // DTO
        directorDto = new DirectorDto(
                1, "Test firstName", "Test lastName",
                "Test@email.com", "Test phoneNumber");
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        DirectorDto invalidDto = new DirectorDto(null,"", null,"wrong email format", null);

        mockMvc.perform(post("/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(directorService.findAll()).thenReturn(List.of(directorDto));

        mockMvc.perform(get("/director/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAppUser").value(1))
                .andExpect(jsonPath("$[0].email").value("Test@email.com"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(directorService.findById(1)).thenReturn(directorDto);

        mockMvc.perform(get("/director/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAppUser").value(1))
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(directorService.findById(1))
                .thenThrow(new DirectorNotFoundException());

        mockMvc.perform(get("/director/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(directorService.save(any(DirectorDto.class))).thenReturn(directorDto);

        mockMvc.perform(post("/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAppUser").value(1))
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }
/*
    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(directorService).delete(1);

        mockMvc.perform(delete("/director/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(directorService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new DirectorService.DirectorNotFoundException())
                .when(directorService).delete(1);

        mockMvc.perform(delete("/director/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(directorService.update(eq(1), any(DirectorDto.class))).thenReturn(directorDto);

        mockMvc.perform(patch("/director/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(directorService.update(eq(1), any(DirectorDto.class)))
                .thenThrow(new DirectorService.DirectorNotFoundException());

        mockMvc.perform(patch("/director/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(directorDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

 */
}