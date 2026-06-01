package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ActivityService;
import com.mns.cda.suivimns.service.entity.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketProgressService {

    private final TicketStatusService statusService;
    private final ActivityService activityService;

    @Transactional
    public Ticket takeTicketInCharge(Ticket ticket, Technician technician, String statusReason) {

        Technician assignedTechnician = ticket.getCurrentTechnician();

        if (assignedTechnician == null || !assignedTechnician.equals(technician)) {
            throw new UnauthorizedTechnicianException();
        }

        activityService.log(technician, "A pris en charge le ticket #" + ticket.getIdTicket());

        return statusService.changeStatus(ticket, StatusEnum.IN_PROGRESS, technician, statusReason);
    }

    @Transactional
    public Ticket resumeTicket(Ticket ticket, AppUser user, String statusReason) {
        if (ticket.getCurrentTechnician() == null) {
            throw new UnauthorizedTechnicianException();
        }

        activityService.log(user, "A repris en charge le ticket #" + ticket.getIdTicket());

        return statusService.changeStatus(ticket, StatusEnum.IN_PROGRESS, user, statusReason);
    }
}
