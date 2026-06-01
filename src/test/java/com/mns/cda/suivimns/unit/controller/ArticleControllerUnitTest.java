package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.ArticleController;
import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dto.entity.ArticleDto;
import com.mns.cda.suivimns.exception.ArticleNotFoundException;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    @MockBean
    private AppUserDao appUserDao;


    private ArticleDto articleDto;
    private Technician technician;
    private AppUserDetails user;

    @BeforeEach
    void setUp() {
        // DTO
        articleDto = new ArticleDto(
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

        ArticleDto invalidDto = new ArticleDto(null,null, null, "", null, null);

        mockMvc.perform(post("/article")
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

        when(articleService.findAll()).thenReturn(List.of(articleDto));

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
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(articleService.findById(1)).thenReturn(articleDto);

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
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(articleService.findById(1))
                .thenThrow(new ArticleNotFoundException());

        mockMvc.perform(get("/article/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {

        when(articleService.save(any(ArticleDto.class), nullable(AppUserDetails.class))).thenReturn(articleDto);

        when(appUserDao.findById(user.getId())).thenReturn(Optional.of(technician));

        mockMvc.perform(post("/article")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idArticle").value(1))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {

        doNothing().when(articleService)
                .delete(eq(1), nullable(AppUserDetails.class));

        mockMvc.perform(delete("/article/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(articleService)
                .delete(eq(1), nullable(AppUserDetails.class));
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new ArticleNotFoundException())
                .when(articleService)
                .delete(eq(1), isNull());

        mockMvc.perform(delete("/article/1")
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

        when(articleService.update(eq(1), any(ArticleDto.class))).thenReturn(articleDto);

        mockMvc.perform(put("/article/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        when(articleService.update(eq(1), any(ArticleDto.class)))
                .thenThrow(new ArticleService.ArticleNotFoundException());

        mockMvc.perform(put("/article/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

 */
}