package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.exception.TicketNotFoundException;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebugService {

    private final TicketDao ticketDao;
    private final TicketMetricsService metricsService;
    private final TicketPriorityService priorityService;


    @Transactional
    public void refreshMetrics(Integer idTicket) {
        Ticket ticket = ticketDao.findById(idTicket).orElseThrow(TicketNotFoundException::new);

        metricsService.refreshTicketMetrics(ticket);
    }

    @Transactional
    public void refreshPriority(Integer idTicket) {
        Ticket ticket = ticketDao.findById(idTicket).orElseThrow(TicketNotFoundException::new);

        priorityService.recalculateCurrentPriority(ticket);
    }
}
