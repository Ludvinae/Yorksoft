package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ThemeController;
import com.mns.cda.suivimns.dto.entity.ThemeDto;
import com.mns.cda.suivimns.enumerate.ThemeEnum;
import com.mns.cda.suivimns.service.entity.ThemeService;
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

    private ThemeDto themeDto;

    @BeforeEach
    void setUp() {
        themeDto = new ThemeDto(
                1, "Test designation", ThemeEnum.BUG, "Test description");
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        ThemeDto invalidDto = new ThemeDto(null,"", ThemeEnum.BUG, null);

        mockMvc.perform(post("/theme")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(themeService.findAll()).thenReturn(List.of(themeDto));

        mockMvc.perform(get("/theme/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idTheme").value(1))
                .andExpect(jsonPath("$[0].designation").value("Test designation"))
                .andExpect(jsonPath("$[0].code").value(ThemeEnum.BUG.name()));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(themeService.findById(1)).thenReturn(themeDto);

        mockMvc.perform(get("/theme/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTheme").value(1))
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.code").value(ThemeEnum.BUG.name()));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(themeService.findById(1))
                .thenThrow(new ThemeService.ThemeNotFoundException());

        mockMvc.perform(get("/theme/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(themeService.save(any(ThemeDto.class))).thenReturn(themeDto);

        mockMvc.perform(post("/theme")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(themeDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.code").value(ThemeEnum.BUG.name()));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(themeService).delete(1);

        mockMvc.perform(delete("/theme/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(themeService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new ThemeService.ThemeNotFoundException())
                .when(themeService).delete(1);

        mockMvc.perform(delete("/theme/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(themeService.update(eq(1), any(ThemeDto.class))).thenReturn(themeDto);

        mockMvc.perform(put("/theme/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(themeDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Test designation"))
                .andExpect(jsonPath("$.code").value(ThemeEnum.BUG.name()));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(themeService.update(eq(1), any(ThemeDto.class)))
                .thenThrow(new ThemeService.ThemeNotFoundException());

        mockMvc.perform(put("/theme/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(themeDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}