package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.*;
import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.service.inter.iHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService implements iHistoryService {

    protected final HistoryDao historyDao;
    protected final StatusDao statusDao;
    protected final AppUserDao appUserDao;

    @Override
    public List<History> findAll() {
        return historyDao.findAll();
    }

    @Override
    public Optional<History> findById(int id) {
        return historyDao.findById(id);
    }

    @Override
    public History save(History history) {
        history.setIdHistory(null);
        return historyDao.save(history);
    }

    @Override
    public void delete(History history) {
        historyDao.delete(history);
    }

    @Override
    public void update(History historyToUpdate, int id) throws iHistoryService.HistoryNotFoundException {
        Optional<History> history = historyDao.findById(id);

        if (history.isEmpty()) {
            throw new iHistoryService.HistoryNotFoundException();
        }

        historyToUpdate.setIdHistory(history.get().getIdHistory());

        historyDao.save(historyToUpdate);
    }

    // METHODS

    @Override
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

    @Override
    public Status getStatus(Integer idTicket) {
        Optional<History> history = historyDao.findLatestByTicket(idTicket);
        if (history.isEmpty()) {
            System.out.println("History not found");
            return null;
        }
        return history.get().getStatus();
    }
}
