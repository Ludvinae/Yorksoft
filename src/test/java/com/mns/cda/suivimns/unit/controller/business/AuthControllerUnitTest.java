package com.mns.cda.suivimns.unit.controller.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.controller.business.AuthController;
import com.mns.cda.suivimns.dto.account.NewUserDto;
import com.mns.cda.suivimns.mapper.entity.DirectorMapper;
import com.mns.cda.suivimns.mapper.entity.ManagerMapper;
import com.mns.cda.suivimns.mapper.entity.TechnicianMapper;
import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.service.entity.AppUserService;
import com.mns.cda.suivimns.service.entity.ClientService;
import com.mns.cda.suivimns.service.entity.DirectorService;
import com.mns.cda.suivimns.service.entity.ManagerService;
import com.mns.cda.suivimns.service.entity.TechnicianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private ClientService clientService;

    @MockBean
    private TechnicianService technicianService;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private DirectorService directorService;

    @MockBean
    private TechnicianMapper technicianMapper;

    @MockBean
    private ManagerMapper managerMapper;

    @MockBean
    private DirectorMapper directorMapper;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    private NewUserDto newUserDto;

    @BeforeEach
    void setUp() {
        newUserDto = new NewUserDto(
                "Test firstName", "Test lastName",
                "Test@email.com", "Test phoneNumber", "TestPassword123", null);
    }

    // =========================
    // CSRF
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn403WithoutCsrf() throws Exception {

        mockMvc.perform(post("/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto)))
                .andExpect(status().isForbidden());
    }

    // =========================
    // VALIDATION
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateManagerWithInvalidEmail() throws Exception {

        NewUserDto invalidDto = new NewUserDto(
                "Test firstName", "Test lastName",
                "wrong email format", "Test phoneNumber", "TestPassword123", null);

        mockMvc.perform(post("/manager")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(managerService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturn400WhenCreateDirectorWithBlankPassword() throws Exception {

        NewUserDto invalidDto = new NewUserDto(
                "Test firstName", "Test lastName",
                "Test@email.com", "Test phoneNumber", "", null);

        mockMvc.perform(post("/director")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(directorService);
    }

    // =========================
    // MANAGER
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateManager() throws Exception {

        Manager manager = new Manager();
        manager.setIdAppUser(1);
        manager.setEmail("Test@email.com");

        when(managerMapper.toNewEntity(any(NewUserDto.class))).thenReturn(manager);

        mockMvc.perform(post("/manager")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(managerService).insert(manager);
    }

    // Un manager n'a pas de champ "rank" (contrairement a Technician) : fournir cette valeur
    // en trop dans le JSON ne doit pas faire echouer la creation, elle doit simplement etre ignoree.
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldIgnoreRankFieldWhenCreatingManager() throws Exception {

        Manager manager = new Manager();
        manager.setIdAppUser(1);
        manager.setEmail("Test@email.com");

        when(managerMapper.toNewEntity(any(NewUserDto.class))).thenReturn(manager);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("firstName", "Test firstName");
        payload.put("lastName", "Test lastName");
        payload.put("email", "Test@email.com");
        payload.put("phoneNumber", "Test phoneNumber");
        payload.put("password", "TestPassword123");
        payload.put("rank", 3);

        mockMvc.perform(post("/manager")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(managerService).insert(manager);
    }

    // =========================
    // DIRECTOR
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateDirector() throws Exception {

        Director director = new Director();
        director.setIdAppUser(1);
        director.setEmail("Test@email.com");

        when(directorMapper.toNewEntity(any(NewUserDto.class))).thenReturn(director);

        mockMvc.perform(post("/director")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(directorService).insert(director);
    }

    // Meme verification que pour /manager : un director n'a pas de champ "rank",
    // en fournir un dans le corps de la requete ne doit pas empecher la creation.
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldIgnoreRankFieldWhenCreatingDirector() throws Exception {

        Director director = new Director();
        director.setIdAppUser(1);
        director.setEmail("Test@email.com");

        when(directorMapper.toNewEntity(any(NewUserDto.class))).thenReturn(director);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("firstName", "Test firstName");
        payload.put("lastName", "Test lastName");
        payload.put("email", "Test@email.com");
        payload.put("phoneNumber", "Test phoneNumber");
        payload.put("password", "TestPassword123");
        payload.put("rank", 3);

        mockMvc.perform(post("/director")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(directorService).insert(director);
    }

    // =========================
    // TECHNICIAN
    // =========================
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateTechnician() throws Exception {

        Technician technician = new Technician();
        technician.setIdAppUser(1);
        technician.setEmail("Test@email.com");
        technician.setRank((byte) 1);

        when(technicianMapper.toNewEntity(any(NewUserDto.class))).thenReturn(technician);

        mockMvc.perform(post("/technician")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(technicianService).insert(technician);
    }
}
