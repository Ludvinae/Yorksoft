package com.mns.cda.suivimns.unit.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.workflow.StateChangeJustification;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.mapper.entity.TicketMapper;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.business.TicketMetricsService;
import com.mns.cda.suivimns.service.entity.TicketService;
import com.mns.cda.suivimns.service.workflow.TicketClosingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Only the assigned technician (or an admin) can close a ticket. A technician who created a ticket
 * closes it only by self-assigning it and following the workflow, not once a manager assigned it to someone else.
 */
@ExtendWith(MockitoExtension.class)
class TicketServiceCloseUnitTest {

    @Mock private TicketDao ticketDao;
    @Mock private AppUserDao appUserDao;
    @Mock private TicketClosingService closingService;
    @Mock private TicketMetricsService metricsService;
    @Mock private TicketMapper ticketMapper;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private final StateChangeJustification justification = new StateChangeJustification("done");

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setCurrentStatus(StatusEnum.SOLVED);
        ticket.setCurrentTechnician(technician(5));

        when(ticketDao.findById(1)).thenReturn(Optional.of(ticket));
    }

    @Test
    void assignedTechnicianCanClose() {
        Technician assigned = ticket.getCurrentTechnician();
        when(appUserDao.findById(5)).thenReturn(Optional.of(assigned));
        when(closingService.closeTicket(eq(ticket), eq(assigned), any())).thenReturn(ticket);

        ticketService.closeTicket(1, justification, new AppUserDetails(assigned));

        verify(closingService).closeTicket(ticket, assigned, "done");
    }

    @Test
    void technicianNotAssignedCannotCloseEvenIfCreator() {
        Technician creator = technician(7);
        when(appUserDao.findById(7)).thenReturn(Optional.of(creator));

        AppUserDetails principal = new AppUserDetails(creator);
        assertThrows(UnauthorizedTechnicianException.class,
                () -> ticketService.closeTicket(1, justification, principal));

        verifyNoInteractions(closingService);
    }

    private static Technician technician(int id) {
        Technician technician = new Technician();
        technician.setIdAppUser(id);
        technician.setFirstName("First");
        technician.setLastName("Last");
        technician.setRank((byte) 1);
        return technician;
    }
}
