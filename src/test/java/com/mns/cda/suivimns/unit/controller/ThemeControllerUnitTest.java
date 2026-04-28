package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ThemeController;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.service.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ThemeController.class)
class ThemeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThemeService themeService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Theme theme;

    @BeforeEach
    void setUp() {
        theme = new Theme();
        theme.setIdTheme(1);
        theme.setDesignation("Test designation");

        // ⚠️ Adapter si @NotNull sur d'autres champs
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(themeService.findAll()).thenReturn(List.of(theme));

        mockMvc.perform(get("/theme/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTheme").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(themeService.findById(1)).thenReturn(Optional.of(theme));

        mockMvc.perform(get("/theme/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTheme").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(themeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/theme/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(themeService.save(any(Theme.class))).thenReturn(theme);

        mockMvc.perform(post("/theme")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theme)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(themeService.findById(1)).thenReturn(Optional.of(theme));

        mockMvc.perform(delete("/theme/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(themeService).delete(theme);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(themeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/theme/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(themeService.update(any(Theme.class), eq(1))).thenReturn(theme);

        mockMvc.perform(put("/theme/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theme)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(themeService.update(any(Theme.class), eq(1)))
                .thenThrow(new ThemeService.ThemeNotFoundException());

        mockMvc.perform(put("/theme/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theme)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}