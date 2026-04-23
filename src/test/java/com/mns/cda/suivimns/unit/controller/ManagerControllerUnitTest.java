package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ManagerController;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.service.inter.iManagerService;
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

@WebMvcTest(controllers = ManagerController.class)
class ManagerControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private iManagerService managerService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Manager manager;

    @BeforeEach
    void setUp() {
        manager = new Manager();
        manager.setIdAppUser(1);
        manager.setPassword("Test password");

        // ⚠️ Adapter si @NotNull sur d'autres champs

    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(managerService.findAll()).thenReturn(List.of(manager));

        mockMvc.perform(get("/manager/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAppUser").value(1));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(managerService.findById(1)).thenReturn(Optional.of(manager));

        mockMvc.perform(get("/manager/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAppUser").value(1));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(managerService.findById(10)).thenReturn(Optional.empty());

        mockMvc.perform(get("/manager/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(managerService.save(any(Manager.class))).thenReturn(manager);

        mockMvc.perform(post("/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(manager)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(managerService.findById(1)).thenReturn(Optional.of(manager));

        mockMvc.perform(delete("/manager/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(managerService).delete(manager);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(managerService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/manager/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}