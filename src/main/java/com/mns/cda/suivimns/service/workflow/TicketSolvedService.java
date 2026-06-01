package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.MissingTicketSolutionException;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TicketSolvedService {

    protected final TicketStatusService statusService;
    protected final ActivityService activityService;

    @Transactional
    public Ticket proposeSolution(Ticket ticket, Technician technician, String reason) {

        Technician assignedTechnician = ticket.getCurrentTechnician();

        if (assignedTechnician == null || !assignedTechnician.equals(technician)) {
            throw new UnauthorizedTechnicianException();
        }

        if (!StringUtils.hasText(ticket.getSolution())) {
            throw new MissingTicketSolutionException();
        }

        activityService.log(technician, "A proposé une solution pour le ticket #" + ticket.getIdTicket());

        return statusService.changeStatus(ticket, StatusEnum.SOLVED, technician, reason);

    }
}
