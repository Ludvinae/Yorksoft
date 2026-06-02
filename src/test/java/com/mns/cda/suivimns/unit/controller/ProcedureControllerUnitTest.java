package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ProcedureController;
import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dto.entity.ProcedureDto;
import com.mns.cda.suivimns.exception.ProcedureNotFoundException;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.ProcedureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProcedureController.class)
class ProcedureControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcedureService procedureService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppUserDao appUserDao;


    private ProcedureDto procedureDto;
    private Technician technician;
    private AppUserDetails user;

    @BeforeEach
    void setUp() {
        // DTO
        procedureDto = new ProcedureDto(
                1, LocalDateTime.now(), LocalDateTime.now(), "Test title", "Test content", 1);

        technician = new Technician();
        technician.setIdAppUser(1);
        technician.setRank((byte) 1);
        user = new AppUserDetails(technician);
    }


    // =========================
    // TEST DTO
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateInvalid() throws Exception {

        ProcedureDto invalidDto = new ProcedureDto(null,null, null, "", null, null);

        mockMvc.perform(post("/procedure")
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

        when(procedureService.findAll()).thenReturn(List.of(procedureDto));

        mockMvc.perform(get("/procedure/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProcedure").value(1))
                .andExpect(jsonPath("$[0].content").value("Test content"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(procedureService.findById(1)).thenReturn(procedureDto);

        mockMvc.perform(get("/procedure/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProcedure").value(1))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(procedureService.findById(1))
                .thenThrow(new ProcedureNotFoundException());

        mockMvc.perform(get("/procedure/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {

        when(procedureService.save(any(ProcedureDto.class), nullable(AppUserDetails.class))).thenReturn(procedureDto);

        when(appUserDao.findById(user.getId())).thenReturn(Optional.of(technician));

        mockMvc.perform(post("/procedure")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(procedureDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProcedure").value(1))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {

        doNothing().when(procedureService)
                .delete(eq(1), nullable(AppUserDetails.class));

        mockMvc.perform(delete("/procedure/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(procedureService)
                .delete(eq(1), nullable(AppUserDetails.class));
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new ProcedureNotFoundException())
                .when(procedureService)
                .delete(eq(1), isNull());

        mockMvc.perform(delete("/procedure/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
/*
    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(procedureService.update(eq(1), any(ProcedureDto.class))).thenReturn(procedureDto);

        mockMvc.perform(put("/procedure/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(procedureDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(procedureService.update(eq(1), any(ProcedureDto.class)))
                .thenThrow(new ProcedureService.ProcedureNotFoundException());

        mockMvc.perform(put("/procedure/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(procedureDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

 */
}