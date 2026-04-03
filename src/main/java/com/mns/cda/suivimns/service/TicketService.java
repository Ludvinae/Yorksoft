package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    protected final TicketDao ticketDao;

    public List<TicketFullWithLatest> getTicketFullWithLatest() {
        return ticketDao.returnTicketFullWithLatest();
    }
}
