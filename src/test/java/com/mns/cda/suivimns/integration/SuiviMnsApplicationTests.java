package com.mns.cda.suivimns.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.workflow.TicketAssignmentDto;
import com.mns.cda.suivimns.dto.workflow.TicketCreationDto;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
class SuiviMnsApplicationTests {

    private final WebApplicationContext context;
    private ObjectMapper mapper = JsonMapper.builder().build();
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
    }


    @Test
    public void callTicketListAsAnonymous_shouldReturnCode403() throws Exception {
        mvc.perform(get("/ticket/list")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("sandraschmidt@yorksoft.fr")
    public void callTicketListAsTechnician_shouldReturnCode200() throws Exception {
        mvc.perform(get("/ticket/list")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("barbara.dupont99@gmail.com")
    public void callTicketDetailsAsOwner_shouldReturnCode200() throws Exception {
        mvc.perform(get("/ticket/1/detail")).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("bdupont@hotmail.com")
    public void callTicketDetailsNotAsOwner_shouldReturnCode403() throws Exception {
        mvc.perform(get("/ticket/1/detail")).andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("sandraschmidt@yorksoft.fr")
    public void callTicketDetailsAsTechnician_shouldReturnCode200() throws Exception {
        mvc.perform(get("/ticket/2/detail")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"TECHNICIAN"})
    public void callDeleteTicketAsTechnician_shouldReturnCode403() throws Exception {
        mvc.perform(delete("/ticket/4")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void callDeleteTicketAsAdmin_shouldReturnCode204() throws Exception {
        mvc.perform(delete("/ticket/5")).andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("sandraschmidt@yorksoft.fr")
    public void callCreateNewTicketWithTechnician_shouldReturnCode201() throws Exception {

        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
            1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        mvc.perform(post("/ticket")
                .contentType(MediaType.APPLICATION_JSON).content(jsonCreation))
                .andExpect(status().isCreated());
    }


    @Test
    @WithUserDetails("jeanvaljean@yorksoft.fr")
    public void callAssignWithManager__shouldReturnCode200() throws Exception {

        Technician technician = new Technician();
        technician.setEmail( "technician@test.fr");
        AppUserDetails principalTech = new AppUserDetails(technician);

        Manager manager = new Manager();
        manager.setEmail( "manager@test.fr");
        AppUserDetails principalManager = new AppUserDetails(manager);

        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        String newTicketJson = mvc.perform(post("/ticket")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(jsonCreation)).andReturn().getResponse().getContentAsString();

        TicketDto newTicket = mapper.readValue(newTicketJson, new TypeReference<TicketDto>(){});
        System.out.println(newTicket);

        TicketAssignmentDto assignment = new TicketAssignmentDto(1, "");

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/assign").contentType(MediaType.APPLICATION_JSON)
                .with(user(principalManager)).)
                .andExpect(status().isOk());
    }


}
