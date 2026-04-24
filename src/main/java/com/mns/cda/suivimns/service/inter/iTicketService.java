package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.flat.TicketCreation;
import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.flat.TicketResponse;
import com.mns.cda.suivimns.dto.flat.TicketUpdatedDto;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface iTicketService {


    Optional<Ticket> findById(int id);

    List<TicketResponse> findAllDto();

    List<TicketFullWithLatest> getAllTicketFullWithLatest();

    List<TicketFullWithLatest> getTicketFullWithLatestByTechnician(int id);

    Ticket save(Ticket ticket);

    void delete(Ticket ticket);

    TicketUpdatedDto update(TicketUpdatedDto ticketToUpdate, int id) throws TicketNotFoundException;

    Ticket forceChangePriority(int priority, int id) throws TicketNotFoundException;

    @Transactional
    Ticket createTicket(TicketCreation ticketDto);

    void addThemeToTicket(Ticket ticket, String designation);

    String getCurrentTheme(Ticket ticket);

    int computePriority(int impact, int urgence, int importance);

    TicketResponse responseToDto(Ticket ticket);

    class TicketNotFoundException extends Exception {
    }
}
