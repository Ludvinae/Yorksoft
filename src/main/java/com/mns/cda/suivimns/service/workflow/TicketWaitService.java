package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.InvalidStatusException;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketWaitService {

    protected final TicketStatusService statusService;
    protected final ActivityService activityService;

    @Transactional
    public Ticket setWaitingStatus(Ticket ticket, Technician technician, String reason, StatusEnum status) {

        Technician assignedTechnician = ticket.getCurrentTechnician();

        if (assignedTechnician == null || !assignedTechnician.equals(technician)) {
            throw new UnauthorizedTechnicianException();
        }

        String target;
        switch (status) {
            case WAITING_CLIENT -> target = "client";
            case WAITING_THIRD_PARTY ->  target = "tiers";
            default -> throw new InvalidStatusException();
        }

        activityService.log(technician,
                "A mis le ticket #" + ticket.getIdTicket() + " en attente d'un " + target,
                ActivityType.TICKET);

        return statusService.changeStatus(ticket, status, technician, reason);
    }
}
