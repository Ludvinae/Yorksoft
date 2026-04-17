package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface iHistoryService {
    List<History> findAll();

    Optional<History> findById(int id);

    void save(History history);

    void delete(History history);

    void update(History historyToUpdate, int id) throws HistoryNotFoundException;

    void updateHistory(Ticket ticket, Integer actorId, String nextStatus);

    Status getStatus(Integer idTicket);

    public static class HistoryNotFoundException extends Exception {
    }
}
