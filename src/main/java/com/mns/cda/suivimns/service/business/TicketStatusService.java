package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketStatusService {

    private final HistoryDao historyDao;

    public Status getCurrentStatus(int idTicket) throws TicketService.TicketNotFoundException {
        History currentHistory = historyDao.findLatestByTicket(idTicket)
                .orElseThrow(TicketService.TicketNotFoundException::new);

        return currentHistory.getStatus();
    }

    public void markPreviousHistoryAsEnded(Ticket ticket) {
        // temp
        History previousHistory = ticket.getHistoryList().get(1);
    }
}
