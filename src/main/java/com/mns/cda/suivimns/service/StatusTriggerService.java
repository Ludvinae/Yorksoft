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

@Service
@RequiredArgsConstructor
public class StatusTriggerService {

    protected final TicketDao ticketDao;
    protected final StatusDao statusDao;
    protected final HistoryDao historyDao;
    protected final AppUserDao appUserDao;

    @Transactional
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

        History history = new History(null, null, null, statusNouveau, ticket, actor);
        historyDao.save(history);

        if (ticket.getHistoryList() == null) {
            ticket.setHistoryList(new ArrayList<>());
        }
        List<History> historyList = ticket.getHistoryList();


        for (History lastHistory : historyList) {
            if (lastHistory.getEndDate() == null) {
                lastHistory.setEndDate(LocalDateTime.now());
            }
        }

        historyList.add(history);
        ticket.setHistoryList(historyList);
        ticketDao.save(ticket);

    }
}
