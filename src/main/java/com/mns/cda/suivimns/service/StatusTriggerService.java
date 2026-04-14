package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusTriggerService {

    protected final TicketDao ticketDao;
    protected final StatusDao statusDao;
    protected final HistoryDao historyDao;
    protected final AppUserDao appUserDao;


    public void updateHistory(Ticket ticket, Integer actorId, String nextStatus) {

        Status statusNouveau = statusDao.findByDesignation(nextStatus)
                .orElseThrow(() -> new RuntimeException("Statut introuvable"));

        AppUser actor;

        if (actorId == null) {
            actor = ticket.getClient();
        }
        else {
            actor = appUserDao.findById(actorId)
                    .orElseThrow(() -> new RuntimeException("User introuvable"));
        }

        if (ticket.getHistoryList() == null) {
            ticket.setHistoryList(new ArrayList<>());
        }

        Optional<History> previousHistory = historyDao.findLatestByTicket(ticket.getIdTicket());
        previousHistory.ifPresent(history -> history.setEndDate(LocalDateTime.now()));

        History history = new History(null, LocalDateTime.now(), null, statusNouveau, ticket, actor);
        historyDao.save(history);

    }
}
