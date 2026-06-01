package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.IllegalStatusTransitionException;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ActivityService;
import com.mns.cda.suivimns.service.entity.HistoryService;
import com.mns.cda.suivimns.service.workflow.StatusTransition;
import com.mns.cda.suivimns.service.workflow.TicketOpeningService;
import com.mns.cda.suivimns.service.workflow.TicketStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TicketOpeningServiceUnitTest {


    @Mock
    private HistoryService historyService;

    @Mock
    private ActivityService activityService;


    @InjectMocks
    private TicketOpeningService openingService;

    private Ticket ticket;
    private AppUser user;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setIdTicket(1);

        user = new AppUser();
    }



    // =========================================================
    // initializeStatus
    // =========================================================
    @Test
    void initializeStatus_shouldInitializeTicket() {

        openingService.initializeStatus(ticket, user);

        assertEquals(StatusEnum.OPEN, ticket.getCurrentStatus());

        verify(activityService).log(
                user,
                "A ouvert le ticket #" + ticket.getIdTicket()
        );

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
                IllegalStatusTransitionException.class,
                () -> openingService.initializeStatus(ticket, user)
        );

        verify(historyService, never()).addHistory(any(), any(), any(), any());
    }

}
