package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.AdminDao;
import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dao.search.TicketSpecification;
import com.mns.cda.suivimns.dto.dashboard.*;
import com.mns.cda.suivimns.dto.dashboard.activity.UserActivity;
import com.mns.cda.suivimns.dto.dashboard.admin.TicketHealthStats;
import com.mns.cda.suivimns.dto.dashboard.admin.UserHealthStats;
import com.mns.cda.suivimns.dto.dashboard.graphs.SoftwareStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TechnicianWorkloadDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.ThemeStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TicketEvolutionPointDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TicketStatusStatDto;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.ActivityService;
import com.mns.cda.suivimns.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TicketDao ticketDao;
    private final AppUserDao appUserDao;
    private final AdminDao adminDao;
    private final SecurityService securityService;
    private final ActivityService activityService;

    public DashboardDto getStats(Integer timeframeInDays, AppUserDetails principal) {

        int timeframe = (timeframeInDays != null && timeframeInDays > 0)
                ? timeframeInDays : 30;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(timeframe);

        if (securityService.isAdmin()) {
            return getAdminStats(startDate, principal);
        }
        if (securityService.isDirector()) {
            return getDirectorStats(startDate, principal);
        }
        if (securityService.isManager()) {
            return getManagerStats(startDate, timeframe, principal);
        }
        if (securityService.isTechnician()) {
            return getTechnicianStats(startDate, timeframe, principal);
        }
        throw new AuthenticationCredentialsNotFoundException("Authentication credentials not found");
    }

    private DashboardAdminDto getAdminStats(LocalDateTime startDate, AppUserDetails principal) {
        Integer id = principal.getId();

        TicketHealthStats ticketHealth = new TicketHealthStats(
                ticketDao.closedTicketsWithoutEndDate(),
                ticketDao.openTicketsWithCloseDate(),
                ticketDao.count(TicketSpecification.hasStatusMismatch(true)),
                ticketDao.count(TicketSpecification.hasAssignmentIssue(true)),
                ticketDao.count(TicketSpecification.hasUnflaggedSlaBreach(true)),
                ticketDao.count(TicketSpecification.isOrphan(true))
        );

        UserHealthStats userHealth = new UserHealthStats(
                appUserDao.countUsersWithoutRole(),
                appUserDao.countUsersWithMultipleRoles(),
                // "{seed-role}" placeholders that DemoPasswordSeeder didn't rehash: those accounts can't log in
                appUserDao.countByPasswordStartingWith("{seed-"),
                appUserDao.countEmployeesInactiveSince(LocalDateTime.now().minusDays(90)),
                appUserDao.countEmployeesNeverActive(),
                adminDao.count()
        );

        List<UserActivity> activities = activityService.activityFeed(id);

        return new DashboardAdminDto(ticketHealth, userHealth, activities);
    }

    private DashboardDirectorDto getDirectorStats(LocalDateTime startDate, AppUserDetails principal) {
        Integer id = principal.getId();

        List<UserActivity> activities = activityService.activityFeed(id);

        return new DashboardDirectorDto(activities);
    }

    private DashboardManagerDto getManagerStats(LocalDateTime startDate, Integer timeframeInDays, AppUserDetails principal) {
        Integer id = principal.getId();

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

        List<UserActivity> activities = activityService.activityFeed(id);

        return new DashboardManagerDto(open, progress, waiting, priority, overdue, unassigned,
                resolutionInMinutes, reponseInMinutes, callInMinutes, affectations,
                closedDay, closedWeek, workload, status, software, theme, activities);
    }

    private DashboardTechnicianDto getTechnicianStats(LocalDateTime startDate, int timeframeInDays, AppUserDetails principal) {
        Integer id = principal.getId();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = now.toLocalDate().atStartOfDay();
        LocalDateTime startOfWeek = now.toLocalDate()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay();

        // Current workload
        int open = ticketDao.countAssignedOpenTickets(id);
        int waiting = ticketDao.countAssignedWaitingTickets(id);
        int critical = ticketDao.countAssignedCriticalTickets(id);
        int overdue = ticketDao.countAssignedOverdueTickets(id);
        int closedDay = ticketDao.countClosedSinceDate(id, today);
        int closedWeek = ticketDao.countClosedSinceDate(id, startOfWeek);

        // Statistics over the selected period
        Double timeToSolve = ticketDao.meanTimeToSolveTickets(id, startDate);
        Double responseTime = ticketDao.meanResponseTime(id, startDate);
        double close = ticketDao.closedTicketCount(id, startDate) * 1.0;
        Integer total = ticketDao.totalTicketCount(id, startDate);
        Double resolution = total > 0 ? close / total : null;
        int closedInPeriod = ticketDao.countClosedSinceDate(id, startDate);
        double closedPerDay = (double) closedInPeriod / timeframeInDays;
        double closedPerWeek = closedPerDay * 7;

        // Charts
        List<TicketStatusStatDto> statusStats = ticketDao.countAssignedTicketsByStatus(id);
        String unit = timeframeInDays <= 31 ? "day" : "week";
        List<TicketEvolutionPointDto> evolution = buildEvolution(id, startDate, unit);

        List<UserActivity> activities = activityService.activityFeed(id);

        return new DashboardTechnicianDto(open, waiting, critical, overdue, closedDay, closedWeek,
                timeframeInDays, timeToSolve, responseTime, resolution, closedInPeriod, closedPerDay, closedPerWeek,
                statusStats, unit, evolution, activities);
    }

    /**
     * One point per day (or per week, starting on Monday like Postgres' date_trunc) from startDate to today,
     * including the empty ones so the chart's time axis has no gaps.
     */
    private List<TicketEvolutionPointDto> buildEvolution(int id, LocalDateTime startDate, String unit) {
        Map<LocalDate, Long> taken = toCountByBucket(ticketDao.countTakenInChargeByPeriod(id, startDate, unit));
        Map<LocalDate, Long> closed = toCountByBucket(ticketDao.countClosedByPeriod(id, startDate, unit));

        boolean weekly = "week".equals(unit);
        LocalDate bucket = weekly
                ? startDate.toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                : startDate.toLocalDate();
        LocalDate end = LocalDate.now();

        List<TicketEvolutionPointDto> points = new ArrayList<>();
        while (!bucket.isAfter(end)) {
            points.add(new TicketEvolutionPointDto(bucket,
                    taken.getOrDefault(bucket, 0L), closed.getOrDefault(bucket, 0L)));
            bucket = weekly ? bucket.plusWeeks(1) : bucket.plusDays(1);
        }
        return points;
    }

    private static Map<LocalDate, Long> toCountByBucket(List<Object[]> rows) {
        Map<LocalDate, Long> counts = new HashMap<>();
        for (Object[] row : rows) {
            LocalDate date = row[0] instanceof java.sql.Date sqlDate ? sqlDate.toLocalDate() : (LocalDate) row[0];
            counts.put(date, ((Number) row[1]).longValue());
        }
        return counts;
    }
}
