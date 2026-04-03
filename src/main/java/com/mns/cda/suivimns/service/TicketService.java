package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TicketService {

    public static class TicketNotFoundException extends Exception {}

    protected final TicketDao ticketDao;

    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    public Optional<Ticket> findById(int id) {
        return ticketDao.findById(id);
    }

    public void save(Ticket ticket) {
        ticket.setIdTicket(null);
        ticketDao.save(ticket);
    }

    public void delete(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    public void update(Ticket ticketToUpdate, int id) throws TicketNotFoundException {
        Optional<Ticket> ticket = ticketDao.findById(id);

        if (ticket.isEmpty()) {
            throw new TicketNotFoundException();
        }

        ticketToUpdate.setIdTicket(ticket.get().getIdTicket());

        ticketDao.save(ticketToUpdate);
    }

    public List<TicketFullWithLatest> getTicketFullWithLatest() {
        /*
        List<TicketFullWithLatest> ticketList = ticketDao.returnTicketFullWithLatest();
        Collections.reverse(ticketList);

        List<TicketFullWithLatest> ticketListParsed = new ArrayList<>();
        HashSet<Integer> ticketIdSet = new HashSet<Integer>();

        for (TicketFullWithLatest ticket : ticketList) {
            if (ticketIdSet.contains(ticket.id())) {
                continue;
            }
            ticketIdSet.add(ticket.id());
            ticketListParsed.add(ticket);
        }
        return ticketListParsed;

         */
        return ticketDao.returnTicketFullWithLatest();
    }
}
