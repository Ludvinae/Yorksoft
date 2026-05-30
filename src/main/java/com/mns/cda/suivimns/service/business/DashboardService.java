package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.dashboard.*;
import com.mns.cda.suivimns.dto.dashboard.graphs.SoftwareStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TechnicianWorkloadDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.ThemeStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TicketStatusStatDto;
import com.mns.cda.suivimns.service.entity.TechnicianService;
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
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(timeframe);

        if (securityService.isAdmin()) {
            return getAdminStats(startDate);
        }
        if (securityService.isDirector()) {
            return getDirectorStats(startDate);
        }
        if (securityService.isManager()) {
            return getManagerStats(startDate, timeframe);
        }
        if (securityService.isTechnician()) {
            return getTechnicianStats(startDate);
        }
        throw new AuthenticationException();
    }

    private DashboardAdminDto getAdminStats(LocalDateTime startDate) {
        int closed = ticketDao.closedTicketsWithoutEndDate();

        return new DashboardAdminDto(closed);
    }

    private DashboardDirectorDto getDirectorStats(LocalDateTime startDate) {
        return new DashboardDirectorDto();
    }

    private DashboardManagerDto getManagerStats(LocalDateTime startDate, Integer timeframeInDays) {

        int open = ticketDao.countOpenTickets();
        int progress = ticketDao.countInProgressTickets();
        int waiting = ticketDao.countWaitingTickets();
        int priority = ticketDao.countPriorityTickets();
        int overdue = ticketDao.countOpenOverdueTickets();
        int unassigned = ticketDao.countUnassignedTickets();

        Double resolution = ticketDao.averageResolutionTime(startDate);
        double resolutionInMinutes = (resolution != null) && (resolution > 0)
                ? resolution / 60 : 0;
        Double response = ticketDao.averageResponseTime(startDate);
        double reponseInMinutes = (response != null) && (response > 0)
                ? response / 60 : 0;
        Double call = ticketDao.averageCallDuration(startDate);
        double callInMinutes = (call != null) && (call > 0) ? call / 60 : 0;
        double affectations = ticketDao.ticketsPerTechnician();
        int closed = ticketDao.ticketsClosed(startDate);
        double closedDay = (double) closed / timeframeInDays;
        double closedWeek = (double) closed / ((double) timeframeInDays / 7);
        /*
        double closedDay = ticketDao.ticketsClosedPerDay(startDate);
        double closedWeek = ticketDao.ticketsClosedPerWeek(startDate);
         */

        List<TechnicianWorkloadDto> workload = ticketDao.countTicketsPerTechnician();
        List<TicketStatusStatDto> status = ticketDao.countTicketsByStatus();
        List<SoftwareStatDto> software = ticketDao.countTicketsBySoftware();
        List<ThemeStatDto> theme = ticketDao.countTicketsByTheme();

        return new DashboardManagerDto(open, progress, waiting, priority, overdue, unassigned,
                resolutionInMinutes, reponseInMinutes, callInMinutes, affectations,
                closedDay, closedWeek, workload, status, software, theme);
    }

    private DashboardTechnicianDto  getTechnicianStats(LocalDateTime startDate) {
        Integer id = securityService.getCurrentUserId();
        if (id == null) {
            throw new TechnicianService.TechnicianNotFoundException();
        }

        int open = ticketDao.countAssignedOpenTickets(id);
        int waiting = ticketDao.countAssignedWaitingTickets(id);
        int critical = ticketDao.countAssignedCriticalTickets(id);
        int overdue = ticketDao.countAssignedOverdueTickets(id);

        Double timeToSolve = ticketDao.meanTimeToSolveTickets(id, startDate);

        return new DashboardTechnicianDto(open, waiting,  critical, overdue, timeToSolve);
    }
}
