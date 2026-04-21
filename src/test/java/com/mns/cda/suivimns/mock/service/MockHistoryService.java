package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.inter.iHistoryService;

import java.util.List;
import java.util.Optional;

public class MockHistoryService implements iHistoryService {
    @Override
    public List<History> findAll() {
        return List.of();
    }

    @Override
    public Optional<History> findById(int id) {
        return Optional.empty();
    }

    @Override
    public History save(History history) {
        return null;
    }

    @Override
    public void delete(History history) {

    }

    @Override
    public History update(History historyToUpdate, int id) throws HistoryNotFoundException {
        return null;
    }

    @Override
    public void updateHistory(Ticket ticket, Integer actorId, String nextStatus) {

    }

    @Override
    public Status getStatus(Integer idTicket) {
        return null;
    }
}
