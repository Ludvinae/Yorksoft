package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    protected final TicketDao ticketDao;

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
