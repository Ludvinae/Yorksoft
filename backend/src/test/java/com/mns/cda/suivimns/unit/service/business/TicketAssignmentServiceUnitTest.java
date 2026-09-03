package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.dao.AssignmentDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.AssignmentConflictException;
import com.mns.cda.suivimns.exception.IllegalStatusTransitionException;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ActivityService;
import com.mns.cda.suivimns.service.workflow.StatusTransition;
import com.mns.cda.suivimns.service.workflow.TicketAssignmentService;
import com.mns.cda.suivimns.service.workflow.TicketStatusService;
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

    @InjectMocks
    private TicketAssignmentService service;

    @Mock
    private ActivityService activityService;

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
        technician.setIdAppUser(5);
        technician.setFirstName("FirstName");
        technician.setLastName("LastName");
    }

    // =========================================================
    // assignTicket
    // =========================================================

    @Test
    void assignTicket_shouldAssignTicketSuccessfully() {


        when(ticketStatusService.changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                manager,
                "Test reason"
        )).thenReturn(ticket);

        Technician newTechnician = new Technician();
        newTechnician.setIdAppUser(9);

        Ticket result = service.assignTicket(
                ticket,
                manager,
                newTechnician,
                "Test reason"
        );

        ArgumentCaptor<Assignment> captor =
                ArgumentCaptor.forClass(Assignment.class);

        verify(assignmentDao).save(captor.capture());

        Assignment savedAssignment = captor.getValue();

        assertEquals(ticket, savedAssignment.getTicket());
        assertEquals(manager, savedAssignment.getManager());
        assertEquals(newTechnician, savedAssignment.getTechnician());

        verify(ticketStatusService).changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                manager,
                "Test reason"
        );

        assertEquals(newTechnician, ticket.getCurrentTechnician());
        assertEquals(manager, ticket.getCurrentManager());

        assertEquals(ticket, result);
    }

    @Test
    void assignTicket_shouldThrowIfTransitionIsIllegal() {

        when(ticketStatusService.changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                manager,
                "Test reason"
        )).thenThrow(new IllegalStatusTransitionException());

        assertThrows(
                IllegalStatusTransitionException.class,
                () -> service.assignTicket(
                        ticket,
                        manager,
                        technician,
                        "Test reason"
                )
        );

        verify(assignmentDao, never()).save(any());
    }

    @Test
    void assignTicket_shouldThrowIfAlreadyAssignedToSameTechnician() {

        ticket.setCurrentTechnician(technician);


        assertThrows(
                AssignmentConflictException.class,
                () -> service.assignTicket(
                        ticket,
                        manager,
                        technician,
                        "Test reason"
                )
        );

        verify(ticketStatusService, never())
                .changeStatus(any(), any(), any(), any());

        verify(assignmentDao, never())
                .save(any());
    }

    @Test
    void assignTicket_shouldCloseCurrentAssignment() {

        Assignment existingAssignment = new Assignment();
        existingAssignment.setEndDate(null);

        when(ticketStatusService.changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                manager,
                "Test reason"
        )).thenReturn(ticket);

        when(assignmentDao.findLatestByTicket(1))
                .thenReturn(Optional.of(existingAssignment));

        service.assignTicket(
                ticket,
                manager,
                technician,
                "Test reason"
        );

        assertNotNull(existingAssignment.getEndDate());

        assertTrue(
                existingAssignment.getEndDate()
                        .isBefore(LocalDateTime.now().plusSeconds(1))
        );
    }

    @Test
    void assignTicket_shouldIgnoreIfNoCurrentAssignmentExists() {

        when(ticketStatusService.changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                manager,
                "Test reason"
        )).thenReturn(ticket);

        when(assignmentDao.findLatestByTicket(1))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() ->
                service.assignTicket(
                        ticket,
                        manager,
                        technician,
                        "Test reason"
                )
        );

        verify(assignmentDao).save(any(Assignment.class));
    }

    @Test
    void assignTicket_shouldSaveExactlyOneAssignment() {


        when(ticketStatusService.changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                manager,
                "Test reason"
        )).thenReturn(ticket);

        service.assignTicket(
                ticket,
                manager,
                technician,
                "Test reason"
        );

        verify(assignmentDao, times(1))
                .save(any(Assignment.class));
    }

    @Test
    void assignTicket_shouldUpdateTicketReferences() {

        when(ticketStatusService.changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                manager,
                "Test reason"
        )).thenReturn(ticket);

        service.assignTicket(
                ticket,
                manager,
                technician,
                "Test reason"
        );

        assertEquals(technician, ticket.getCurrentTechnician());
        assertEquals(manager, ticket.getCurrentManager());
    }
}
