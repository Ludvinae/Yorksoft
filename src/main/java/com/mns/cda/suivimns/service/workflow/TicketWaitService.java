package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mns.cda.suivimns.service.workflow.StatusTransition.canTransition;

@Service
@RequiredArgsConstructor
public class TicketWaitService {

    protected final TicketStatusService statusService;

    public Ticket setWaitingStatus(Ticket ticket, Technician technician, String reason, StatusEnum status) {

        Technician assignedTechnician = ticket.getCurrentTechnician();

        if (assignedTechnician == null || !assignedTechnician.equals(technician)) {
            throw new TicketProgressService.UnauthorizedTechnicianException();
        }

        if (canTransition(ticket.getCurrentStatus(), status)) {
            throw new StatusTransition.IllegalStatusTransitionException();
        }

        return statusService.changeStatus(ticket, status, technician, reason);
    }
}
