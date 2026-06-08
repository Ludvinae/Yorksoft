package com.mns.cda.suivimns.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.workflow.*;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    // private ObjectMapper mapper = JsonMapper.builder().build();
    @Autowired
    private ObjectMapper mapper;
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
    @WithMockUser("CLIENT")
    public void callCreateNewTicketWithClient_shouldReturnCode403() throws Exception {

        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        mvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonCreation))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails("jeanvaljean@yorksoft.fr")
    public void callAssignWithManager__shouldReturnCode200() throws Exception {

        Technician technician = new Technician();
        technician.setEmail( "technician@test.fr");
        technician.setIdAppUser(5);
        technician.setRank((byte) 1);
        AppUserDetails principalTech = new AppUserDetails(technician);

        Manager manager = new Manager();
        manager.setEmail( "manager@test.fr");
        manager.setIdAppUser(4);
        AppUserDetails principalManager = new AppUserDetails(manager);

        // Creation de ticket
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        String newTicketJson = mvc.perform(post("/ticket")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(jsonCreation)).andReturn().getResponse().getContentAsString();

        TicketDto newTicket = mapper.readValue(newTicketJson, TicketDto.class);

        // Assignation
        TicketAssignmentDto assignment = new TicketAssignmentDto(5, "");
        String assignmentJson = mapper.writeValueAsString(assignment);

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/assign").contentType(MediaType.APPLICATION_JSON)
                .with(user(principalManager)).content(assignmentJson))
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails("sandraschmidt@yorksoft.fr")
    public void callStartProgressWithAssignedTechnician_shouldReturnCode200() throws Exception {

        Technician technician = new Technician();
        technician.setEmail( "technician@test.fr");
        technician.setIdAppUser(5);
        technician.setRank((byte) 1);
        AppUserDetails principalTech = new AppUserDetails(technician);

        Manager manager = new Manager();
        manager.setEmail( "manager@test.fr");
        manager.setIdAppUser(4);
        AppUserDetails principalManager = new AppUserDetails(manager);

        // Creation de ticket
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        String newTicketJson = mvc.perform(post("/ticket")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(jsonCreation))
                .andReturn().getResponse().getContentAsString();

        TicketDto newTicket = mapper.readValue(newTicketJson, TicketDto.class);

        // Assignation
        TicketAssignmentDto assignment = new TicketAssignmentDto(5, "");
        String assignmentJson = mapper.writeValueAsString(assignment);

        String assignedTicket = mvc.perform(post("/ticket/" + newTicket.idTicket() + "/assign").contentType(MediaType.APPLICATION_JSON)
                        .with(user(principalManager)).content(assignmentJson))
                .andReturn().getResponse().getContentAsString();

        // Prise en charge
        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/start-progress").contentType(MediaType.APPLICATION_JSON)
                .with(user(principalTech)).content(assignedTicket))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("sandraschmidt@yorksoft.fr")
    public void callSetWaitingStatusWithAssignedTechnician_shouldReturnCode200() throws Exception {

        Technician technician = new Technician();
        technician.setEmail( "technician@test.fr");
        technician.setIdAppUser(5);
        technician.setRank((byte) 1);
        AppUserDetails principalTech = new AppUserDetails(technician);

        Manager manager = new Manager();
        manager.setEmail( "manager@test.fr");
        manager.setIdAppUser(4);
        AppUserDetails principalManager = new AppUserDetails(manager);

        // Creation de ticket
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        String newTicketJson = mvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(jsonCreation))
                .andReturn().getResponse().getContentAsString();

        TicketDto newTicket = mapper.readValue(newTicketJson, TicketDto.class);

        // Assignation
        TicketAssignmentDto assignment = new TicketAssignmentDto(5, "");
        String assignmentJson = mapper.writeValueAsString(assignment);

        String assignedTicket = mvc.perform(post("/ticket/" + newTicket.idTicket() + "/assign").contentType(MediaType.APPLICATION_JSON)
                        .with(user(principalManager)).content(assignmentJson))
                .andReturn().getResponse().getContentAsString();

        // Prise en charge
        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/start-progress").contentType(MediaType.APPLICATION_JSON)
                        .with(user(principalTech)).content(assignedTicket));

        // Set ticket to WAITING_CLIENT
        TicketWaitDto waitDto = new TicketWaitDto(StatusEnum.WAITING_CLIENT,
                "Not enough infos");
        String waitDtoJson =  mapper.writeValueAsString(waitDto);

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/wait")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(waitDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("sandraschmidt@yorksoft.fr")
    public void callResumeProgressWithAssignedTechnician_shouldReturnCode200() throws Exception {

        Technician technician = new Technician();
        technician.setEmail( "technician@test.fr");
        technician.setIdAppUser(5);
        technician.setRank((byte) 1);
        AppUserDetails principalTech = new AppUserDetails(technician);

        Manager manager = new Manager();
        manager.setEmail( "manager@test.fr");
        manager.setIdAppUser(4);
        AppUserDetails principalManager = new AppUserDetails(manager);

        // Creation de ticket
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        String newTicketJson = mvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(jsonCreation))
                .andReturn().getResponse().getContentAsString();

        TicketDto newTicket = mapper.readValue(newTicketJson, TicketDto.class);

        // Assignation
        TicketAssignmentDto assignment = new TicketAssignmentDto(5, "");
        String assignmentJson = mapper.writeValueAsString(assignment);

        String assignedTicket = mvc.perform(post("/ticket/" + newTicket.idTicket() + "/assign").contentType(MediaType.APPLICATION_JSON)
                        .with(user(principalManager)).content(assignmentJson))
                .andReturn().getResponse().getContentAsString();

        // Prise en charge
        String justification = mapper.writeValueAsString(new StateChangeJustification(""));

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/start-progress").contentType(MediaType.APPLICATION_JSON)
                .with(user(principalTech)).content(assignedTicket));

        // Set ticket to WAITING_CLIENT
        TicketWaitDto waitDto = new TicketWaitDto(StatusEnum.WAITING_CLIENT,
                "Not enough infos");
        String waitDtoJson =  mapper.writeValueAsString(waitDto);

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/wait")
                        .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(waitDtoJson))
                .andExpect(status().isOk());

        // Reprends la prise en charge
        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/resume-progress")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(justification))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("TECHNICIAN")
    public void callUpdateDescriptionWithTechnician_shouldReturnCode200() throws Exception {

        Technician technician = new Technician();
        technician.setEmail( "sandraschmidt@yorksoft.fr");
        technician.setIdAppUser(5);
        technician.setRank((byte) 1);
        AppUserDetails principalTech = new AppUserDetails(technician);

        Manager manager = new Manager();
        manager.setEmail( "manager@test.fr");
        manager.setIdAppUser(4);
        AppUserDetails principalManager = new AppUserDetails(manager);

        // Creation de ticket
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        String newTicketJson = mvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(jsonCreation))
                .andReturn().getResponse().getContentAsString();

        TicketDto newTicket = mapper.readValue(newTicketJson, TicketDto.class);

        // Assignation
        TicketAssignmentDto assignment = new TicketAssignmentDto(5, "");
        String assignmentJson = mapper.writeValueAsString(assignment);

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/assign")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalManager)).content(assignmentJson));

        // Prise en charge
        String justification = mapper.writeValueAsString(new StateChangeJustification(""));

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/start-progress")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(justification));

        // Update ticket with solution
        TicketDescriptionDto description = new TicketDescriptionDto("", "Solution pour l'incident");
        String descriptionJson = mapper.writeValueAsString(description);

        mvc.perform(patch("/ticket/" + newTicket.idTicket() + "/description")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(descriptionJson))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("TECHNICIAN")
    public void callSolveTicketWithTechnician_shouldReturnCode200() throws Exception {

        Technician technician = new Technician();
        technician.setEmail( "sandraschmidt@yorksoft.fr");
        technician.setIdAppUser(5);
        technician.setRank((byte) 1);
        AppUserDetails principalTech = new AppUserDetails(technician);

        Manager manager = new Manager();
        manager.setEmail( "manager@test.fr");
        manager.setIdAppUser(4);
        AppUserDetails principalManager = new AppUserDetails(manager);

        // Creation de ticket
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        String newTicketJson = mvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(jsonCreation))
                .andReturn().getResponse().getContentAsString();

        TicketDto newTicket = mapper.readValue(newTicketJson, TicketDto.class);

        // Assignation
        TicketAssignmentDto assignment = new TicketAssignmentDto(5, "");
        String assignmentJson = mapper.writeValueAsString(assignment);

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/assign")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalManager)).content(assignmentJson));

        String justification = mapper.writeValueAsString(new StateChangeJustification(""));

        // Prise en charge
        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/start-progress")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(justification));

        // Update ticket with solution
        TicketDescriptionDto description = new TicketDescriptionDto("", "Solution pour l'incident");
        String descriptionJson = mapper.writeValueAsString(description);

        mvc.perform(patch("/ticket/" + newTicket.idTicket() + "/description")
                        .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(descriptionJson));

        // Solve ticket
        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/solve")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(justification))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("sandraschmidt@yorksoft.fr")
    public void callCloseTicketWithTechnician_shouldReturnCode200() throws Exception {

        Technician technician = new Technician();
        technician.setEmail( "technician@test.fr");
        technician.setIdAppUser(5);
        technician.setRank((byte) 1);
        AppUserDetails principalTech = new AppUserDetails(technician);

        Manager manager = new Manager();
        manager.setEmail( "manager@test.fr");
        manager.setIdAppUser(4);
        AppUserDetails principalManager = new AppUserDetails(manager);

        // Creation de ticket
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);
        String jsonCreation = mapper.writeValueAsString(ticket);

        String newTicketJson = mvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(jsonCreation))
                .andReturn().getResponse().getContentAsString();

        TicketDto newTicket = mapper.readValue(newTicketJson, TicketDto.class);

        // Assignation
        TicketAssignmentDto assignment = new TicketAssignmentDto(5, "Justification");
        String assignmentJson = mapper.writeValueAsString(assignment);

        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/assign")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalManager)).content(assignmentJson));

        String justification = mapper.writeValueAsString(new StateChangeJustification(""));


        // Prise en charge
        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/start-progress")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(justification));


        // Update ticket with solution
        TicketDescriptionDto description = new TicketDescriptionDto("", "Solution pour l'incident");
        String descriptionJson = mapper.writeValueAsString(description);

        mvc.perform(patch("/ticket/" + newTicket.idTicket() + "/description")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(descriptionJson));

        // Solve ticket
        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/solve")
                        .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(justification));


        // Close ticket
        mvc.perform(post("/ticket/" + newTicket.idTicket() + "/close")
                .contentType(MediaType.APPLICATION_JSON).with(user(principalTech)).content(justification))
                .andExpect(status().isOk());
    }
}
