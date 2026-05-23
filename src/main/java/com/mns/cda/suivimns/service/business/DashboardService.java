package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.dashboard.*;
import com.mns.cda.suivimns.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TicketDao ticketDao;
    private final SecurityService securityService;

    public DashboardDto getStats(Integer timeframeInDays)
            throws AuthenticationException {

        int timeframe = (timeframeInDays != null && timeframeInDays > 0)
                ? timeframeInDays : 30;

        if (securityService.isAdmin()) {
            return getAdminStats(timeframe);
        }
        if (securityService.isDirector()) {
            return getDirectorStats(timeframe);
        }
        if (securityService.isManager()) {
            return getManagerStats(timeframe);
        }
        if (securityService.isTechnician()) {
            return getTechnicianStats(timeframe);
        }
        throw new AuthenticationException();
    }

    private DashboardAdminDto getAdminStats(Integer timeframeInDays) {
        return new DashboardAdminDto();
    }

    private DashboardDirectorDto getDirectorStats(Integer timeframeInDays) {
        return new DashboardDirectorDto();
    }

    private DashboardManagerDto getManagerStats(Integer timeframeInDays) {

        LocalDateTime now = LocalDateTime.now();

        int open = ticketDao.countOpenTickets();
        int progress = ticketDao.countInProgressTickets();
        int waiting = ticketDao.countWaitingTickets();
        int priority = ticketDao.countPriorityTickets();
        int overdue = ticketDao.countOverdueTickets(now.minusHours(24), now.minusHours(8), now.minusHours(2));
        int unassigned = ticketDao.countUnassignedTickets();

        Double resolution = ticketDao.averageResolutionTime(now.minusDays(timeframeInDays));
        double resolutionInMinutes = (resolution != null) && (resolution > 0)
                ? resolution / 60 : 0;
        Double response = ticketDao.averageResponseTime(now.minusDays(timeframeInDays));
        double reponseInMinutes = (response != null) && (response > 0)
                ? response / 60 : 0;


        List<TicketStatusStatDto> ticketsByStatus = ticketDao.countTicketsByStatus();

        return new DashboardManagerDto(open, progress, waiting, priority, overdue, unassigned,
                resolutionInMinutes, reponseInMinutes, ticketsByStatus);
    }

    private DashboardTechnicianDto  getTechnicianStats(Integer timeframeInDays) {
        return new DashboardTechnicianDto();
    }
}
