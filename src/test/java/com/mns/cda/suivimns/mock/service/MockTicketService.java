package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.dto.flat.TicketCreation;
import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.flat.TicketResponse;
import com.mns.cda.suivimns.dto.flat.TicketUpdatedDto;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.inter.iTicketService;

import java.util.List;
import java.util.Optional;

public class MockTicketService implements iTicketService {
    @Override
    public List<TicketResponse> findAllDto() {
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
    public TicketUpdatedDto update(TicketUpdatedDto ticketToUpdate, int id) throws TicketNotFoundException {
        return null;
    }

    @Override
    public Ticket forceChangePriority(int priority, int id) throws TicketNotFoundException {
        return null;
    }

    @Override
    public Ticket createTicket(TicketCreation ticketDto) {
        return null;
    }

    @Override
    public void addThemeToTicket(Ticket ticket, String designation) {

    }

    @Override
    public String getCurrentTheme(Ticket ticket) {
        return "";
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
