package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.HistoryService;
import com.mns.cda.suivimns.service.StatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.mns.cda.suivimns.service.workflow.StatusTransition.canTransition;

@Service
@RequiredArgsConstructor
public class TicketStatusService {

    private final HistoryDao historyDao;
    private final StatusTransition transition;
    private final HistoryService historyService;

    public static class MissingCurrentHistoryException extends RuntimeException {
    }

    public StatusEnum getCurrentStatus(Ticket ticket) {
        return ticket.getCurrentStatus();
    }

    private void closeCurrentHistory(Ticket ticket)
            throws MissingCurrentHistoryException {

        History currentHistory = historyDao.findLatestByTicket(ticket.getIdTicket())
                .orElseThrow(MissingCurrentHistoryException::new);

        currentHistory.setEndDate(LocalDateTime.now());
    }

    @Transactional
    public void initializeStatus(Ticket ticket, AppUser user)
            throws StatusService.StatusNotFoundException {

        // Verifier que le ticket n'a pas déja un statut
        if (ticket.getCurrentStatus() != null) {
            throw new IllegalStateException("Ticket already initialized");
        }

        ticket.setCurrentStatus(StatusEnum.OPEN);
        historyService.addHistory(ticket, user, StatusEnum.OPEN, null);
    }

    @Transactional
    public Ticket changeStatus(
            Ticket ticket,
            StatusEnum newStatus,
            AppUser user,
            String statusReason
    ) throws StatusTransition.IllegalStatusTransitionException,
            StatusService.StatusNotFoundException,
            MissingCurrentHistoryException {

        StatusEnum currentStatus = ticket.getCurrentStatus();
        if (!canTransition(currentStatus, newStatus)) {
            throw new StatusTransition.IllegalStatusTransitionException();
        }

        // Ajoute la date de fin sur le status actuel
        closeCurrentHistory(ticket);

        // ajout d'une entrée dans l'historique
        historyService.addHistory(ticket, user, newStatus, statusReason);

        // Met à jour le ticket avec le nouveau statut
        ticket.setCurrentStatus(newStatus);

        return ticket;
    }
}
