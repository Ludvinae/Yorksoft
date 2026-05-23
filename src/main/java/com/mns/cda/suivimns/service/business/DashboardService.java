package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.dashboard.DashboardDto;
import com.mns.cda.suivimns.dto.dashboard.TicketStatusStatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TicketDao ticketDao;


    public DashboardDto getStats(Integer timeframeInDays) {
        int timeframe = timeframeInDays != null ? timeframeInDays : 30;
        LocalDateTime now = LocalDateTime.now();

        int open = ticketDao.countOpenTickets();
        int progress = ticketDao.countInProgressTickets();
        int waiting = ticketDao.countWaitingTickets();
        int priority = ticketDao.countPriorityTickets();
        int overdue = ticketDao.countOverdueTickets(now.minusHours(24), now.minusHours(8), now.minusHours(2));
        int unassigned = ticketDao.countUnassignedTickets();

        Double resolution = ticketDao.averageResolutionTime(now.minusDays(timeframe));
        double resolutionInMinutes = (resolution != null) && (resolution > 0)
                ? resolution / 60 : 0;
        Double response = ticketDao.averageResponseTime(now.minusDays(timeframe));
        double reponseInMinutes = (response != null) && (response > 0)
                ? response / 60 : 0;


        List<TicketStatusStatDto> ticketsByStatus = ticketDao.countTicketsByStatus();

        return new DashboardDto(open, progress, waiting, priority, overdue, unassigned,
                resolutionInMinutes, reponseInMinutes, ticketsByStatus);
    }
}
