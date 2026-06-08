package com.mns.cda.suivimns.helper;

import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.workflow.*;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.mapper.entity.TicketMapper;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketWorkflowTestHelper {

    private final TicketService ticketService;
    private final TicketMapper mapper;

    StateChangeJustification justification = new StateChangeJustification("Reason");

    private TicketDto createTicket(AppUserDetails principal) {
        TicketCreationDto ticket = new TicketCreationDto("Title", "description",
                1, 1, 1, 1, 1, 1);

        return ticketService.save(ticket, principal);
    }

    private TicketDto assignTicket(AppUserDetails principal, int idTechnician, int idTicket) {
        TicketAssignmentDto assignment = new TicketAssignmentDto(idTechnician, "");

        return ticketService.assignTicket(idTicket,  assignment, principal);
    }

    private TicketDto takeTicketInCharge(AppUserDetails principal, int idTicket) {
        return ticketService.takeTicketInCharge(idTicket, justification, principal);
    }

    private TicketDto setTicketWaiting(AppUserDetails principal, int idTicket) {
        TicketWaitDto waitDto = new TicketWaitDto(StatusEnum.WAITING_CLIENT, "Reason");

        return ticketService.setWaitingStatus(idTicket, waitDto, principal);
    }

    private void updateTicketSolution(AppUserDetails principal, int idTicket) {
        TicketDescriptionDto description = new TicketDescriptionDto("Description", "Solution");

        ticketService.update(idTicket, description, principal);
    }

    private TicketDto solveTicket(AppUserDetails principal, int idTicket) {
        return ticketService.solveTicket(idTicket, justification, principal);
    }

    private TicketDto closeTicket(AppUserDetails principal, int idTicket) {
        return ticketService.closeTicket(idTicket, justification, principal);
    }

    public Ticket getOpenTestTicket(AppUserDetails principal) {
        return mapper.toEntity(createTicket(principal));
    }

    public Ticket getAssignedTestTicket(AppUserDetails principal, AppUserDetails managerDetails) {
        TicketDto ticket = createTicket(principal);

        return mapper.toEntity(assignTicket(managerDetails, principal.getId(), ticket.idTicket()));
    }

    public Ticket getInProgressTestTicket(AppUserDetails principal, AppUserDetails managerDetails) {
        TicketDto ticket = createTicket(principal);
        assignTicket(managerDetails, principal.getId(), ticket.idTicket());

        return mapper.toEntity(takeTicketInCharge(principal, ticket.idTicket()));
    }

    public Ticket getSolvedTestTicket(AppUserDetails principal, AppUserDetails managerDetails) {
        TicketDto ticket = createTicket(principal);
        assignTicket(managerDetails, principal.getId(), ticket.idTicket());
        takeTicketInCharge(principal, ticket.idTicket());
        updateTicketSolution(principal, ticket.idTicket());

        return mapper.toEntity(solveTicket(principal, ticket.idTicket()));
    }

    public Ticket getClosedTestTicket(AppUserDetails principal, AppUserDetails managerDetails) {
        TicketDto ticket = createTicket(principal);
        assignTicket(managerDetails, principal.getId(), ticket.idTicket());
        takeTicketInCharge(principal, ticket.idTicket());
        updateTicketSolution(principal, ticket.idTicket());
        solveTicket(principal, ticket.idTicket());

        return mapper.toEntity(closeTicket(principal, ticket.idTicket()));
    }
}
