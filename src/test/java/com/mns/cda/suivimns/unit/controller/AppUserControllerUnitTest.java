package com.mns.cda.suivimns.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.AppUserController;
import com.mns.cda.suivimns.dto.account.NewUserDto;
import com.mns.cda.suivimns.dto.entity.AppUserDto;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.model.Admin;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppUserController.class)
class AppUserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private ObjectMapper objectMapper;


    private AppUserDto appUserDto;
    private NewUserDto newUserDto;

    @BeforeEach
    void setUp() {
        // DTO
        appUserDto = new AppUserDto(
                1, "Test firstName", "Test lastName",
                "Test@email.com", "Test phoneNumber");

        newUserDto = new NewUserDto(
                "Test firstName", "Test lastName",
                "Test@email.com", "Test phoneNumber", "TestPassword123", (byte) 1);
    }

    // =========================
    // TEST CSRF
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn403WithoutCsrf() throws Exception {

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto)))
                .andExpect(status().isForbidden());
    }

    // =========================
    // TEST DTO
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateInvalid() throws Exception {

        NewUserDto invalidDto = new NewUserDto("", null, "wrong email format",
                null, null, (byte) 1);

        mockMvc.perform(post("/user")
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

        when(appUserService.findAll()).thenReturn(List.of(appUserDto));

        mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAppUser").value(1))
                .andExpect(jsonPath("$[0].email").value("Test@email.com"));
    }

    // =========================
    // GET BY ID - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnById() throws Exception {

        when(appUserService.findById(1)).thenReturn(appUserDto);

        mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAppUser").value(1))
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }

    // =========================
    // GET BY ID - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenNotFound() throws Exception {

        when(appUserService.findById(1))
                .thenThrow(new AppUserNotFoundException());

        mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // CREATE
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreate() throws Exception {

        when(appUserService.save(any(NewUserDto.class))).thenReturn(appUserDto);

        mockMvc.perform(post("/user")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAppUser").value(1))
                .andExpect(jsonPath("$.email").value("Test@email.com"));
    }

    // =========================
    // DELETE - OK
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDelete() throws Exception {

        doNothing().when(appUserService).delete(1);

        mockMvc.perform(delete("/user/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(appUserService).delete(1);
    }

    // =========================
    // DELETE - NOT FOUND
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenDeleteNotFound() throws Exception {

        doThrow(new AppUserNotFoundException())
                .when(appUserService).delete(1);

        mockMvc.perform(delete("/user/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // =========================
    // UPDATE - OK
    // =========================
    @Test
    void shouldUpdate() throws Exception {

        Admin admin = new Admin();
        admin.setIdAppUser(1);
        admin.setEmail("admin@test.fr");

        AppUserDetails principal = new AppUserDetails(admin);

        when(appUserService.update(eq(1), any(AppUserDto.class), any(AppUserDetails.class)))
                .thenReturn(appUserDto);

        mockMvc.perform(patch("/user/1")
                        .with(user(principal))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUserDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("Test@email.com"));

        verify(appUserService).update(eq(1), any(AppUserDto.class), any(AppUserDetails.class));
    }

    // =========================
    // UPDATE - NOT FOUND
    // =========================
    @Test
    void shouldReturn404WhenUpdateFails() throws Exception {

        Admin admin = new Admin();
        admin.setIdAppUser(1);
        admin.setEmail("admin@test.fr");

        AppUserDetails principal = new AppUserDetails(admin);

        when(appUserService.update(eq(1), any(AppUserDto.class), any(AppUserDetails.class)))
                .thenThrow(new AppUserNotFoundException());

        mockMvc.perform(patch("/user/1")
                        .with(user(principal))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUserDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}