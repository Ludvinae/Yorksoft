package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.CommentController;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.CommentService;
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

    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.setIdComment(1);
        comment.setContent("Test content");

        // ⚠️ Adapter si @NotNull sur d'autres champs
        Ticket ticket = new Ticket();
        ticket.setIdTicket(1);
        comment.setTicket(ticket);
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(commentService.findAll()).thenReturn(List.of(comment));

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

        when(commentService.findById(1)).thenReturn(Optional.of(comment));

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

        when(commentService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/comment/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(commentService.save(any(Comment.class))).thenReturn(comment);

        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(commentService.findById(1)).thenReturn(Optional.of(comment));

        mockMvc.perform(delete("/comment/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(commentService).delete(comment);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(commentService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/comment/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(commentService.update(any(Comment.class), eq(1))).thenReturn(comment);

        mockMvc.perform(put("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(commentService.update(any(Comment.class), eq(1)))
                .thenThrow(new CommentService.CommentNotFoundException());

        mockMvc.perform(put("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}