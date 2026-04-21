package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.dto.TicketCreation;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.TicketResponse;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.inter.iTicketService;

import java.util.List;
import java.util.Optional;

public class MockTicketService implements iTicketService {
    @Override
    public List<Ticket> findAll() {
        return List.of();
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<TicketFullWithLatest> getAllTicketFullWithLatest() {
        return List.of();
    }

    @Override
    public List<TicketFullWithLatest> getTicketFullWithLatestByTechnician(int id) {
        return List.of();
    }

    @Override
    public Ticket save(Ticket ticket) {
        return null;
    }

    @Override
    public void delete(Ticket ticket) {

    }

    @Override
    public void update(Ticket ticketToUpdate, int id) throws TicketNotFoundException {

    }

    @Override
    public Ticket createTicket(TicketCreation ticketDto) {
        return null;
    }

    @Override
    public void addThemeToTicket(Ticket ticket, String designation) {

    }

    @Override
    public int computePriority(int impact, int urgence, int importance) {
        return 0;
    }

    @Override
    public TicketResponse responseToDto(Ticket ticket) {
        return null;
    }
}
