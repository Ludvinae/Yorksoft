package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketWaitService {

    protected final TicketStatusService statusService;

    public Ticket setWaitingStatus(Ticket ticket, Technician technician, String reason, StatusEnum status) {

        Technician assignedTechnician = ticket.getCurrentTechnician();

        if (assignedTechnician == null || !assignedTechnician.equals(technician)) {
            throw new UnauthorizedTechnicianException();
        }

        return statusService.changeStatus(ticket, status, technician, reason);
    }
}
