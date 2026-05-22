package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.dashboard.DashboardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TicketDao ticketDao;

    public DashboardDto getStats() {

        int open = ticketDao.countOpenTickets();
        int progress = ticketDao.countInProgressTickets();
        int waiting = ticketDao.countWaitingTickets();
        int priority = ticketDao.countPriorityTickets();
        int overdue = ticketDao.countOverdueTickets();
        int unassigned = ticketDao.countUnassignedTickets();

        return new DashboardDto(open, progress, waiting, priority, overdue, unassigned);
    }
}
