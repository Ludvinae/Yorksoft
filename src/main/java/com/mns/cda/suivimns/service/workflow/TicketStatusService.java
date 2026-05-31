package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.*;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.HistoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketStatusService {

    private final HistoryDao historyDao;
    private final StatusTransition transition;
    private final HistoryService historyService;

    public StatusEnum getCurrentStatus(Ticket ticket) {
        return ticket.getCurrentStatus();
    }

    private void closeCurrentHistory(Ticket ticket) {

        History currentHistory = historyDao.findLatestByTicket(ticket.getIdTicket())
                .orElseThrow(MissingCurrentHistoryException::new);

        currentHistory.setEndDate(LocalDateTime.now());
    }

    @Transactional
    public void initializeStatus(Ticket ticket, AppUser user) {

        // Verifier que le ticket n'a pas déja un statut
        if (ticket.getCurrentStatus() != null) {
            throw new IllegalStateException("Ticket already initialized");
        }

        ticket.setCurrentStatus(StatusEnum.OPEN);
        historyService.addHistory(ticket, user, StatusEnum.OPEN, null);
    }

    @Transactional
    public Ticket changeStatus(Ticket ticket,StatusEnum newStatus,AppUser user, String statusReason) {

        StatusEnum currentStatus = ticket.getCurrentStatus();

        if (user == null) {
            throw new IllegalArgumentException();
        }

        if (!transition.canTransition(currentStatus, newStatus)) {
            throw new IllegalStatusTransitionException();
        }

        if (transition.statusRequiresAssignedTechnician(newStatus) && ticket.getCurrentTechnician() == null) {
            throw new MissingAssignedTechnicianException();
        }

        if (transition.statusRequiresJustification(currentStatus, newStatus) && !StringUtils.hasText(statusReason)) {
            throw new MissingStatusTransitionJustificationException();
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
