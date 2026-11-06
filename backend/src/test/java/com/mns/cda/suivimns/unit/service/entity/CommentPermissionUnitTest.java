package com.mns.cda.suivimns.unit.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.CommentDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.flat.PostCommentDto;
import com.mns.cda.suivimns.exception.UnauthorizedTechnicianException;
import com.mns.cda.suivimns.mapper.entity.CommentMapper;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.security.TrialActionLimiter;
import com.mns.cda.suivimns.service.entity.ActivityService;
import com.mns.cda.suivimns.service.entity.CommentService;
import com.mns.cda.suivimns.service.workflow.TicketAssignmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Rank 0 (trial accounts) and rank 1 technicians comment only the tickets they created or are assigned to;
 * rank 2+ technicians and the other employee roles comment any ticket.
 */
@ExtendWith(MockitoExtension.class)
class CommentPermissionUnitTest {

    @Mock private CommentDao commentDao;
    @Mock private CommentMapper commentMapper;
    @Mock private TicketDao ticketDao;
    @Mock private AppUserDao appUserDao;
    @Mock private ActivityService activityService;
    @Mock private TicketAssignmentService assignmentService;
    @Mock private TrialActionLimiter trialActionLimiter;

    @InjectMocks
    private CommentService commentService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setIdTicket(3);
        ticket.setCurrentTechnician(technician(5, (byte) 1));
    }

    @Test
    void trialAccountCanCommentTicketItCreated() {
        when(assignmentService.isTicketCreator(ticket, 1)).thenReturn(true);

        assertTrue(commentService.canComment(ticket, details(technician(1, (byte) 0))));
    }

    @Test
    void lowRankTechnicianCanCommentTicketAssignedToThem() {
        assertTrue(commentService.canComment(ticket, details(technician(5, (byte) 1))));
        verifyNoInteractions(assignmentService);
    }

    @Test
    void lowRankTechnicianCannotCommentOtherTickets() {
        when(assignmentService.isTicketCreator(ticket, 1)).thenReturn(false);
        when(assignmentService.isTicketCreator(ticket, 2)).thenReturn(false);

        assertFalse(commentService.canComment(ticket, details(technician(1, (byte) 0))));
        assertFalse(commentService.canComment(ticket, details(technician(2, (byte) 1))));
    }

    @Test
    void rankTwoTechnicianCanCommentAnyTicket() {
        assertTrue(commentService.canComment(ticket, details(technician(8, (byte) 2))));
        verifyNoInteractions(assignmentService);
    }

    @Test
    void managerCanCommentAnyTicket() {
        Manager manager = new Manager();
        manager.setIdAppUser(9);
        manager.setFirstName("First");
        manager.setLastName("Last");

        assertTrue(commentService.canComment(ticket, new AppUserDetails(manager)));
    }

    @Test
    void saveIsRefusedWhenTheTechnicianCannotComment() {
        when(ticketDao.findById(3)).thenReturn(Optional.of(ticket));
        when(assignmentService.isTicketCreator(ticket, 1)).thenReturn(false);

        AppUserDetails trial = details(technician(1, (byte) 0));
        assertThrows(UnauthorizedTechnicianException.class,
                () -> commentService.save(new PostCommentDto("hello", 3), trial));

        verifyNoInteractions(commentDao, activityService, trialActionLimiter);
    }

    private static AppUserDetails details(Technician technician) {
        return new AppUserDetails(technician);
    }

    private static Technician technician(int id, byte rank) {
        Technician technician = new Technician();
        technician.setIdAppUser(id);
        technician.setFirstName("First");
        technician.setLastName("Last");
        technician.setRank(rank);
        return technician;
    }
}
