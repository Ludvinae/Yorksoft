package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.AssignmentService;
import com.mns.cda.suivimns.service.business.StatusTransition;
import com.mns.cda.suivimns.service.business.TicketAssignmentService;
import com.mns.cda.suivimns.service.business.TicketStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketAssignmentServiceUnitTest {

    @Mock
    private AssignmentDao assignmentDao;

    @Mock
    private TicketStatusService ticketStatusService;

    @Mock
    private StatusTransition transition;

    @InjectMocks
    private TicketAssignmentService service;

    private Ticket ticket;
    private Manager manager;
    private Technician technician;

    @BeforeEach
    void setUp() {

        ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setCurrentStatus(StatusEnum.OPEN);

        manager = new Manager();

        technician = new Technician();
    }

    // =========================================================
    // assignTicket
    // =========================================================

    @Test
    void assignTicket_shouldAssignTicketSuccessfully() {

        when(transition.canTransition(
                StatusEnum.OPEN,
                StatusEnum.ASSIGNED
        )).thenReturn(true);

        Ticket result = service.assignTicket(
                ticket,
                manager,
                technician
        );

        ArgumentCaptor<Assignment> captor =
                ArgumentCaptor.forClass(Assignment.class);

        verify(assignmentDao).save(captor.capture());

        Assignment savedAssignment = captor.getValue();

        assertEquals(ticket, savedAssignment.getTicket());
        assertEquals(manager, savedAssignment.getManager());
        assertEquals(technician, savedAssignment.getTechnician());

        verify(ticketStatusService).changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                manager
        );

        assertEquals(technician, ticket.getCurrentTechnician());
        assertEquals(manager, ticket.getCurrentManager());

        assertEquals(ticket, result);
    }

    @Test
    void assignTicket_shouldThrowIfTransitionIsIllegal() {

        when(transition.canTransition(
                StatusEnum.OPEN,
                StatusEnum.ASSIGNED
        )).thenReturn(false);

        assertThrows(
                StatusTransition.IllegalStatusTransitionException.class,
                () -> service.assignTicket(
                        ticket,
                        manager,
                        technician
                )
        );

        verify(ticketStatusService, never())
                .changeStatus(any(), any(), any());

        verify(assignmentDao, never())
                .save(any());
    }

    @Test
    void assignTicket_shouldThrowIfAlreadyAssignedToSameTechnician() {

        ticket.setCurrentTechnician(technician);

        when(transition.canTransition(
                StatusEnum.OPEN,
                StatusEnum.ASSIGNED
        )).thenReturn(true);

        assertThrows(
                AssignmentService.AssignmentConflictException.class,
                () -> service.assignTicket(
                        ticket,
                        manager,
                        technician
                )
        );

        verify(ticketStatusService, never())
                .changeStatus(any(), any(), any());

        verify(assignmentDao, never())
                .save(any());
    }

    @Test
    void assignTicket_shouldCloseCurrentAssignment() {

        Assignment existingAssignment = new Assignment();
        existingAssignment.setEndDate(null);

        when(transition.canTransition(
                StatusEnum.OPEN,
                StatusEnum.ASSIGNED
        )).thenReturn(true);

        when(assignmentDao.findLatestByTicket(1))
                .thenReturn(Optional.of(existingAssignment));

        service.assignTicket(
                ticket,
                manager,
                technician
        );

        assertNotNull(existingAssignment.getEndDate());

        assertTrue(
                existingAssignment.getEndDate()
                        .isBefore(LocalDateTime.now().plusSeconds(1))
        );
    }

    @Test
    void assignTicket_shouldIgnoreIfNoCurrentAssignmentExists() {

        when(transition.canTransition(
                StatusEnum.OPEN,
                StatusEnum.ASSIGNED
        )).thenReturn(true);

        when(assignmentDao.findLatestByTicket(1))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() ->
                service.assignTicket(
                        ticket,
                        manager,
                        technician
                )
        );

        verify(assignmentDao).save(any(Assignment.class));
    }

    @Test
    void assignTicket_shouldSaveExactlyOneAssignment() {

        when(transition.canTransition(
                StatusEnum.OPEN,
                StatusEnum.ASSIGNED
        )).thenReturn(true);

        service.assignTicket(
                ticket,
                manager,
                technician
        );

        verify(assignmentDao, times(1))
                .save(any(Assignment.class));
    }

    @Test
    void assignTicket_shouldUpdateTicketReferences() {

        when(transition.canTransition(
                StatusEnum.OPEN,
                StatusEnum.ASSIGNED
        )).thenReturn(true);

        service.assignTicket(
                ticket,
                manager,
                technician
        );

        assertEquals(technician, ticket.getCurrentTechnician());
        assertEquals(manager, ticket.getCurrentManager());
    }
}
