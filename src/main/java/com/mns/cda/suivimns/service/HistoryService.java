package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {

    public static class HistoryNotFoundException extends Exception {}

    protected final HistoryDao historyDao;
    protected final StatusDao statusDao;
    protected final AppUserDao appUserDao;

    public List<History> findAll() {
        return historyDao.findAll();
    }

    public Optional<History> findById(int id) {
        return historyDao.findById(id);
    }

    public void save(History history) {
        history.setIdHistory(null);
        historyDao.save(history);
    }

    public void delete(History history) {
        historyDao.delete(history);
    }

    public void update(History historyToUpdate, int id) throws HistoryService.HistoryNotFoundException {
        Optional<History> history = historyDao.findById(id);

        if (history.isEmpty()) {
            throw new HistoryService.HistoryNotFoundException();
        }

        historyToUpdate.setIdHistory(history.get().getIdHistory());

        historyDao.save(historyToUpdate);
    }

    // METHODS

    public void updateHistory(Ticket ticket, Integer actorId, String nextStatus) {

        Status status = statusDao.findByDesignation(nextStatus)
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

        History history = new History(null, LocalDateTime.now(), null, status, ticket, actor);
        historyDao.save(history);

    }

    public Status getStatus(Integer idTicket) {
        Optional<History> history = historyDao.findLatestByTicket(idTicket);
        if (history.isEmpty()) {
            System.out.println("History not found");
            return null;
        }
        return history.get().getStatus();
    }
}
