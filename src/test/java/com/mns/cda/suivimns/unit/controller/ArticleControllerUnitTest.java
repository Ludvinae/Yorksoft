package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ArticleController;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.service.ArticleService;
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

@WebMvcTest(controllers = ArticleController.class)
class ArticleControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setIdArticle(1);
        article.setContent("Test content");

        // ⚠️ Adapter si @NotNull sur d'autres champs
        Knowledge knowledge = new Knowledge();
        knowledge.setIdKnowledge(1);
        article.setKnowledge(knowledge);
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAll() throws Exception {

        when(articleService.findAll()).thenReturn(List.of(article));

        mockMvc.perform(get("/article/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idArticle").value(1))
                .andExpect(jsonPath("$[0].content").value("Test content"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    void shouldReturnById() throws Exception {

        when(articleService.findById(1)).thenReturn(Optional.of(article));

        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idArticle").value(1))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(articleService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    void shouldCreate() throws Exception {

        when(articleService.save(any(Article.class))).thenReturn(article);

        mockMvc.perform(post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    void shouldDelete() throws Exception {

        when(articleService.findById(1)).thenReturn(Optional.of(article));

        mockMvc.perform(delete("/article/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(articleService).delete(article);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        when(articleService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/article/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        when(articleService.update(any(Article.class), eq(1))).thenReturn(article);

        mockMvc.perform(put("/article/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(articleService.update(any(Article.class), eq(1)))
                .thenThrow(new ArticleService.ArticleNotFoundException());

        mockMvc.perform(put("/article/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}