package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.TicketCreation;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.TicketResponse;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface iTicketService {
    List<Ticket> findAll();

    Optional<Ticket> findById(int id);

    List<TicketFullWithLatest> getAllTicketFullWithLatest();

    List<TicketFullWithLatest> getTicketFullWithLatestByTechnician(int id);

    void save(Ticket ticket);

    void delete(Ticket ticket);

    void update(Ticket ticketToUpdate, int id) throws TicketNotFoundException;

    @Transactional
    Ticket createTicket(TicketCreation ticketDto);

    void addThemeToTicket(Ticket ticket, String designation);

    int computePriority(int impact, int urgence, int importance);

    TicketResponse responseToDto(Ticket ticket);

    class TicketNotFoundException extends Exception {
    }
}
