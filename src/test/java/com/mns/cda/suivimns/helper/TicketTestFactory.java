package com.mns.cda.suivimns.helper;

import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.workflow.*;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.mapper.entity.TicketMapper;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketTestFactory {

    private final TicketService ticketService;
    private final TechnicianDao technicianDao;
    private final ManagerDao managerDao;

    private final StateChangeJustification justification = new StateChangeJustification("Reason");

    private static final String MANAGER_EMAIL = "jeanvaljean@yorksoft.fr";
    private static final String TECHNICIAN_N1_EMAIL = "sandraschmidt@yorksoft.fr";
    private static final String TECHNICIAN_N3_EMAIL = "thomasmuller@yorksoft.fr";

    public AppUserDetails getTechnicianN1Principal() {
        return new AppUserDetails(technicianDao.findByEmail(TECHNICIAN_N1_EMAIL).orElseThrow());
    }

    public AppUserDetails getTechnicianN3Principal() {
        return new AppUserDetails(technicianDao.findByEmail(TECHNICIAN_N3_EMAIL).orElseThrow());
    }

    public AppUserDetails getManagerPrincipal() {
        return new AppUserDetails(managerDao.findByEmail(MANAGER_EMAIL).orElseThrow());
    }

    private TicketDto createTicket(AppUserDetails technicianDetails) {
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);

        return ticketService.save(ticket, technicianDetails);
    }

    private TicketDto assignTicket(AppUserDetails managerDetails, int idTechnician, int idTicket) {
        TicketAssignmentDto assignment = new TicketAssignmentDto(idTechnician, "");

        return ticketService.assignTicket(idTicket,  assignment, managerDetails);
    }

    private TicketDto takeTicketInCharge(AppUserDetails technicianDetails, int idTicket) {
        return ticketService.takeTicketInCharge(idTicket, justification, technicianDetails);
    }

    private TicketDto setTicketWaiting(AppUserDetails technicianDetails, int idTicket) {
        TicketWaitDto waitDto = new TicketWaitDto(StatusEnum.WAITING_CLIENT, "Reason");

        return ticketService.setWaitingStatus(idTicket, waitDto, technicianDetails);
    }

    private TicketDto takeBackInCharge(AppUserDetails technicianDetails, int idTicket) {
        return ticketService.resumeTicket(idTicket, justification, technicianDetails);
    }


    private void updateTicketSolution(AppUserDetails technicianDetails, int idTicket) {
        TicketDescriptionDto description = new TicketDescriptionDto("Description", "Solution");

        ticketService.update(idTicket, description, technicianDetails);
    }

    private TicketDto solveTicket(AppUserDetails technicianDetails, int idTicket) {
        return ticketService.solveTicket(idTicket, justification, technicianDetails);
    }

    private TicketDto closeTicket(AppUserDetails technicianDetails, int idTicket) {
        return ticketService.closeTicket(idTicket, justification, technicianDetails);
    }

    public TicketDto open(AppUserDetails technicianDetails) {
        return createTicket(technicianDetails);
    }

    public TicketDto assigned(AppUserDetails technicianDetails, AppUserDetails managerDetails) {
        Integer id = open(technicianDetails).idTicket();

        System.out.println("Manager id = " + managerDetails.getId());
        System.out.println("Technician id = " + technicianDetails.getId());

        return assignTicket(managerDetails, technicianDetails.getId(), id);
    }

    public TicketDto inProgress(AppUserDetails technicianDetails, AppUserDetails managerDetails) {
        Integer id = assigned(technicianDetails, managerDetails).idTicket();

        return takeTicketInCharge(technicianDetails, id);
    }

    public TicketDto waiting(AppUserDetails technicianDetails, AppUserDetails managerDetails) {
        Integer id = inProgress(technicianDetails, managerDetails).idTicket();

        return setTicketWaiting(technicianDetails, id);
    }

    public TicketDto resumeProgress(AppUserDetails technicianDetails, AppUserDetails managerDetails) {
        Integer id = waiting(technicianDetails, managerDetails).idTicket();

        return takeBackInCharge(technicianDetails, id);
    }

    public Integer solutionUpdated(AppUserDetails technicianDetails, AppUserDetails managerDetails) {
        Integer id = inProgress(technicianDetails, managerDetails).idTicket();
        updateTicketSolution(technicianDetails, id);

        return id;
    }

    public TicketDto solved(AppUserDetails technicianDetails, AppUserDetails managerDetails) {
        Integer id = solutionUpdated(technicianDetails, managerDetails);

        return solveTicket(technicianDetails, id);
    }

    public TicketDto closed(AppUserDetails technicianDetails, AppUserDetails managerDetails) {
        Integer id = solved(technicianDetails, managerDetails).idTicket();

        return closeTicket(technicianDetails, id);
    }
}
