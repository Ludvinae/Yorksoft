package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.IllegalStatusTransitionException;
import com.mns.cda.suivimns.exception.MissingCurrentHistoryException;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.HistoryService;
import com.mns.cda.suivimns.service.workflow.StatusTransition;
import com.mns.cda.suivimns.service.workflow.TicketStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketStatusServiceUnitTest {

    @Mock
    private HistoryDao historyDao;

    @Mock
    private StatusTransition transition;

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private TicketStatusService service;

    private Ticket ticket;
    private AppUser user;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setIdTicket(1);

        user = new AppUser();
    }

    // =========================================================
    // getCurrentStatus
    // =========================================================

    @Test
    void getCurrentStatus_shouldReturnCurrentStatus() {

        ticket.setCurrentStatus(StatusEnum.OPEN);

        StatusEnum result = service.getCurrentStatus(ticket);

        assertEquals(StatusEnum.OPEN, result);
    }

    // =========================================================
    // initializeStatus
    // =========================================================

    @Test
    void initializeStatus_shouldInitializeTicket() throws Exception {

        service.initializeStatus(ticket, user);

        assertEquals(StatusEnum.OPEN, ticket.getCurrentStatus());

        verify(historyService).addHistory(
                ticket,
                user,
                StatusEnum.OPEN,
                null
        );
    }

    @Test
    void initializeStatus_shouldThrowIfAlreadyInitialized() {

        ticket.setCurrentStatus(StatusEnum.OPEN);

        assertThrows(
                IllegalStateException.class,
                () -> service.initializeStatus(ticket, user)
        );

        verify(historyService, never()).addHistory(any(), any(), any(), any());
    }

    // =========================================================
    // changeStatus
    // =========================================================

    @Test
    void changeStatus_shouldChangeStatus() throws Exception {

        ticket.setCurrentStatus(StatusEnum.OPEN);

        History history = new History();

        when(transition.canTransition(StatusEnum.OPEN, StatusEnum.ASSIGNED))
                .thenReturn(true);

        when(historyDao.findLatestByTicket(1))
                .thenReturn(Optional.of(history));

        service.changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                user,
                "Test reason"
        );

        assertEquals(StatusEnum.ASSIGNED, ticket.getCurrentStatus());

        assertNotNull(history.getEndDate());

        verify(historyService).addHistory(
                ticket,
                user,
                StatusEnum.ASSIGNED,
                "Test reason"
        );
    }

    @Test
    void changeStatus_shouldThrowIfTransitionIsIllegal() {

        ticket.setCurrentStatus(StatusEnum.OPEN);

        when(transition.canTransition(StatusEnum.OPEN, StatusEnum.CLOSED))
                .thenReturn(false);

        assertThrows(
                IllegalStatusTransitionException.class,
                () -> service.changeStatus(
                        ticket,
                        StatusEnum.CLOSED,
                        user,
                        "Test reason"
                )
        );

        verify(historyDao, never()).findLatestByTicket(any());
        verify(historyService, never()).addHistory(any(), any(), any(), any());
    }

    @Test
    void changeStatus_shouldThrowIfCurrentHistoryMissing() {

        ticket.setCurrentStatus(StatusEnum.OPEN);

        when(transition.canTransition(StatusEnum.OPEN, StatusEnum.ASSIGNED))
                .thenReturn(true);

        when(historyDao.findLatestByTicket(1))
                .thenReturn(Optional.empty());

        assertThrows(
                MissingCurrentHistoryException.class,
                () -> service.changeStatus(
                        ticket,
                        StatusEnum.ASSIGNED,
                        user,
                        "Test reason"
                )
        );

        verify(historyService, never()).addHistory(any(), any(), any(), any());
    }

    // =========================================================
    // closeCurrentHistory behavior
    // =========================================================

    @Test
    void changeStatus_shouldCloseCurrentHistory() throws Exception {

        ticket.setCurrentStatus(StatusEnum.OPEN);

        History history = new History();
        history.setEndDate(null);

        when(transition.canTransition(StatusEnum.OPEN, StatusEnum.ASSIGNED))
                .thenReturn(true);

        when(historyDao.findLatestByTicket(1))
                .thenReturn(Optional.of(history));

        service.changeStatus(
                ticket,
                StatusEnum.ASSIGNED,
                user,
                "Test reason"
        );

        assertNotNull(history.getEndDate());
        assertTrue(history.getEndDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}
