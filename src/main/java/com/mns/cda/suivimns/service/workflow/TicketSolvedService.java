package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketSolvedService {

    protected final TicketStatusService statusService;

    public Ticket proposeSolution(Ticket ticket, Technician technician, String reason) {

        Technician assignedTechnician = ticket.getCurrentTechnician();

        if (assignedTechnician == null || !assignedTechnician.equals(technician)) {
            throw new TicketProgressService.UnauthorizedTechnicianException();
        }

        return statusService.changeStatus(ticket, StatusEnum.SOLVED, technician, reason);

    }
}
