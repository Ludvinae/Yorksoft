package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mns.cda.suivimns.service.workflow.StatusTransition.canTransition;

@Service
@RequiredArgsConstructor
public class TicketProgressService {

    public static class UnauthorizedTechnicianException extends RuntimeException {}

    private final TicketStatusService statusService;

    @Transactional
    public Ticket takeTicketInCharge(Ticket ticket, Technician technician, String statusReason) {

        Technician assignedTechnician = ticket.getCurrentTechnician();

        if (assignedTechnician == null || !assignedTechnician.equals(technician)) {
            throw new UnauthorizedTechnicianException();
        }

        return statusService.changeStatus(ticket, StatusEnum.IN_PROGRESS, technician, statusReason);
    }

    @Transactional
    public Ticket resumeTicket(Ticket ticket, AppUser user, String statusReason) {
        if (ticket.getCurrentTechnician() == null) {
            throw new UnauthorizedTechnicianException();
        }

        return statusService.changeStatus(ticket, StatusEnum.IN_PROGRESS, user, statusReason);
    }
}
