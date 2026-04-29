package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.CommentController;
import com.mns.cda.suivimns.dto.CommentDto;
import com.mns.cda.suivimns.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
class CommentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        // DTO
        commentDto = new CommentDto(
                1, "Test content", LocalDateTime.now(), LocalDateTime.now(), 1, 1);
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    void shouldReturn400WhenCreateInvalid() throws Exception {

        CommentDto invalidDto = new CommentDto(null,"", null, null, null, null);

        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(commentService.findAll()).thenReturn(List.of(commentDto));

        mockMvc.perform(get("/comment/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idComment").value(1))
                .andExpect(jsonPath("$[0].content").value("Test content"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(commentService.findById(1)).thenReturn(commentDto);

        mockMvc.perform(get("/comment/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idComment").value(1))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(commentService.findById(1))
                .thenThrow(new CommentService.CommentNotFoundException());

        mockMvc.perform(get("/comment/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(commentService.save(any(CommentDto.class))).thenReturn(commentDto);

        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idComment").value(1))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        doNothing().when(commentService).delete(1);

        mockMvc.perform(delete("/comment/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(commentService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new CommentService.CommentNotFoundException())
                .when(commentService).delete(1);

        mockMvc.perform(delete("/comment/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(commentService.update(eq(1), any(CommentDto.class))).thenReturn(commentDto);

        mockMvc.perform(put("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(commentService.update(eq(1), any(CommentDto.class)))
                .thenThrow(new CommentService.CommentNotFoundException());

        mockMvc.perform(put("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}