package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.dashboard.graphs.SoftwareStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TechnicianWorkloadDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.ThemeStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TicketStatusStatDto;
import com.mns.cda.suivimns.dto.details.TicketDetailComment;
import com.mns.cda.suivimns.dto.details.TicketDetailDto;
import com.mns.cda.suivimns.dto.details.TicketDetailKnowledge;
import com.mns.cda.suivimns.dto.details.TicketDetailProcedure;
import com.mns.cda.suivimns.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Integer>, JpaSpecificationExecutor<Ticket> {

    // Technician dashboard
    // KPIs

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        JOIN t.currentTechnician ct
        WHERE t.closeDate IS null
        AND ct.idAppUser = :id
    """)
    int countAssignedOpenTickets(int id);

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        JOIN t.currentTechnician ct
        WHERE t.closeDate IS null
        AND ct.idAppUser = :id
        AND t.currentStatus IN ('WAITING_CLIENT', 'WAITING_THIRD_PARTY')
    """)
    int countAssignedWaitingTickets(int id);

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        JOIN t.currentTechnician ct
        WHERE t.closeDate IS null
        AND ct.idAppUser = :id
        AND t.currentPriority > 75
    """)
    int countAssignedCriticalTickets(int id);

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        JOIN t.currentTechnician ct
        WHERE t.closeDate IS null
        AND ct.idAppUser = :id
        AND t.overdue = true
    """)
    int countAssignedOverdueTickets(int id);


    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.closeDate >= :startDate
        AND t.currentTechnician.idAppUser = :id
    """)
    int countClosedSinceDate(int id, LocalDateTime startDate);


    @Query("""
        SELECT COUNT(DISTINCT(t))
        FROM Ticket t
        JOIN t.currentTechnician ct ON ct.idAppUser = :id
        JOIN t.assignmentList a
        JOIN a.technician te ON te.idAppUser = :id
        WHERE t.closeDate IS NOT null
        AND a.assignmentDate > :startDate
    """)
    Integer closedTicketCount(int  id, LocalDateTime startDate);

    @Query("""
        SELECT COUNT(DISTINCT(t))
        FROM Ticket t
        JOIN t.assignmentList a
        JOIN a.technician te ON te.idAppUser = :id
        WHERE a.assignmentDate > :startDate
    """)
    Integer totalTicketCount(int  id, LocalDateTime startDate);


    @Query(value = """
        SELECT AVG(ticket_duration)
        FROM (
            SELECT 
                t.id_ticket,
                SUM(EXTRACT(EPOCH FROM (h.end_date - h.start_date))) AS ticket_duration
            FROM ticket t
            JOIN history h ON h.id_ticket = t.id_ticket
            JOIN status s ON s.id_status = h.id_status
            WHERE t.id_current_technician = :id
              AND t.close_date >= :startDate
              AND s.code = 'IN_PROGRESS'
              AND h.end_date IS NOT NULL
            GROUP BY t.id_ticket
        ) durations
    """, nativeQuery = true)
    Double meanTimeToSolveTickets(int id, LocalDateTime startDate);

    // Average time between an assignment to the technician and the ticket's next move to IN_PROGRESS
    // (close to zero for self-assigned tickets, which are taken in charge in the same action)
    @Query(value = """
        SELECT AVG(EXTRACT(EPOCH FROM (first_progress.start_date - a.assignment_date)))
        FROM assignment a
        JOIN LATERAL (
            SELECT MIN(h.start_date) AS start_date
            FROM history h
            JOIN status s ON s.id_status = h.id_status
            WHERE h.id_ticket = a.id_ticket
              AND s.code = 'IN_PROGRESS'
              AND h.start_date >= a.assignment_date
        ) first_progress ON first_progress.start_date IS NOT NULL
        WHERE a.id_technician = :id
          AND a.assignment_date >= :startDate
    """, nativeQuery = true)
    Double meanResponseTime(int id, LocalDateTime startDate);




    // graphiques



    // Manager dashboard
    // Dashboard KPIs
    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.closeDate IS null
    """)
    int countOpenTickets();

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.currentStatus = 'IN_PROGRESS'
        AND t.closeDate IS null
    """)
    int countInProgressTickets();

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.currentStatus = 'WAITING_CLIENT'
        OR t.currentStatus = 'WAITING_THIRD_PARTY'
    """)
    int countWaitingTickets();

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.closeDate IS null
        AND t.currentPriority > 65
    """)
    int countPriorityTickets();

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.closeDate IS NULL
        AND t.overdue = true
    """)
    int countOpenOverdueTickets();

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.closeDate IS null
        AND t.currentTechnician IS null
    """)
    int countUnassignedTickets();


    @Query(value = """
        SELECT AVG(EXTRACT(EPOCH FROM (close_date - open_date)))
        FROM ticket
        WHERE close_date IS NOT NULL
        AND open_date >= :startDate
    """, nativeQuery = true)
    Double averageResolutionTime(LocalDateTime startDate);

    @Query(value = """
        SELECT AVG(EXTRACT(EPOCH FROM (first_in_progress.start_date - t.open_date)))
        FROM ticket t
        JOIN (
            SELECT h.id_ticket, MIN(h.start_date) AS start_date
            FROM history h
            JOIN status s ON h.id_status = s.id_status
            WHERE s.code = 'IN_PROGRESS'
            GROUP BY h.id_ticket
        ) first_in_progress ON t.id_ticket = first_in_progress.id_ticket
        WHERE t.open_date >= :startDate
    """, nativeQuery = true)
    Double averageResponseTime(LocalDateTime startDate);

    @Query(value = """
        SELECT AVG(t.callDuration)
        FROM Ticket t
        WHERE t.openDate >= :startDate
    """)
    Double averageCallDuration(LocalDateTime startDate);

    @Query("""
        SELECT CAST(COUNT(t) AS double) /(
           (SELECT COUNT(te)
            FROM Technician te))
        FROM Ticket t
        WHERE t.closeDate IS NULL
        AND t.currentTechnician IS NOT null
    """)
    Double ticketsPerTechnician();

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.closeDate IS NOT null
        AND t.closeDate >= :startDate
    """)
    Integer ticketsClosed(LocalDateTime startDate);

    @Query("""
        SELECT CAST(COUNT(t) AS double) /
                      COUNT(DISTINCT(FUNCTION('DATE', t.modificationDate)))
               FROM Ticket t
               WHERE t.closeDate IS NOT NULL
               AND t.closeDate >= :startDate
    """)
    Double ticketsClosedPerDay(LocalDateTime startDate);

    @Query("""
        SELECT CAST(COUNT(t) AS double) /
                      COUNT(DISTINCT(FUNCTION('DATE_TRUNC', 'week', t.modificationDate)))
               FROM Ticket t
               WHERE t.closeDate IS NOT NULL
               AND t.closeDate >= :startDate
    """)
    Double ticketsClosedPerWeek(LocalDateTime startDate);

    // graphiques
    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.graphs.TechnicianWorkloadDto(
            ct.firstName, ct.lastName, COUNT(t))
        FROM Ticket t
        JOIN t.currentTechnician ct
        WHERE t.closeDate IS null
        AND t.currentTechnician IS NOT null
        GROUP BY ct.firstName, ct.lastName
        ORDER BY COUNT(t) DESC
        LIMIT 10
    """)
    List<TechnicianWorkloadDto> countTicketsPerTechnician();

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.graphs.TicketStatusStatDto(
            t.currentStatus, COUNT(t), s.displayOrder)
        FROM Ticket t, Status s
        WHERE s.code = t.currentStatus
        AND t.closeDate IS null
        GROUP BY t.currentStatus, s.displayOrder
    """)
    List<TicketStatusStatDto> countTicketsByStatus();

    // Technician dashboard charts

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.graphs.TicketStatusStatDto(
            t.currentStatus, COUNT(t), s.displayOrder)
        FROM Ticket t, Status s
        WHERE s.code = t.currentStatus
        AND t.closeDate IS null
        AND t.currentTechnician.idAppUser = :id
        GROUP BY t.currentStatus, s.displayOrder
    """)
    List<TicketStatusStatDto> countAssignedTicketsByStatus(int id);

    // Tickets the technician took in charge (their first IN_PROGRESS entry on the ticket), per day or week
    @Query(value = """
        SELECT CAST(date_trunc(CAST(:unit AS text), first_start) AS date) AS bucket, COUNT(*)
        FROM (
            SELECT h.id_ticket, MIN(h.start_date) AS first_start
            FROM history h
            JOIN status s ON s.id_status = h.id_status
            WHERE s.code = 'IN_PROGRESS'
              AND h.id_app_user = :id
            GROUP BY h.id_ticket
        ) taken
        WHERE first_start >= :startDate
        GROUP BY bucket
    """, nativeQuery = true)
    List<Object[]> countTakenInChargeByPeriod(int id, LocalDateTime startDate, String unit);

    @Query(value = """
        SELECT CAST(date_trunc(CAST(:unit AS text), close_date) AS date) AS bucket, COUNT(*)
        FROM ticket
        WHERE id_current_technician = :id
          AND close_date >= :startDate
        GROUP BY bucket
    """, nativeQuery = true)
    List<Object[]> countClosedByPeriod(int id, LocalDateTime startDate, String unit);

    // Active tickets by age since opening: bucket 0 = under 1 day, 1 = 1-3 days, 2 = 3-7 days, 3 = 7 days and more.
    // "now" comes from the JVM, which also writes open_date (zone-less LocalDateTime)
    @Query(value = """
        SELECT CASE
                   WHEN open_date > CAST(:now AS timestamp) - INTERVAL '1 day' THEN 0
                   WHEN open_date > CAST(:now AS timestamp) - INTERVAL '3 days' THEN 1
                   WHEN open_date > CAST(:now AS timestamp) - INTERVAL '7 days' THEN 2
                   ELSE 3
               END AS bucket,
               COUNT(*)
        FROM ticket
        WHERE id_current_technician = :id
          AND close_date IS NULL
        GROUP BY bucket
    """, nativeQuery = true)
    List<Object[]> countActiveTicketsByAge(int id, LocalDateTime now);

    @Query(value = """
        SELECT CAST(date_trunc(CAST(:unit AS text), close_date) AS date) AS bucket,
               SUM(CASE WHEN close_date <= sla_deadline THEN 1 ELSE 0 END),
               SUM(CASE WHEN close_date > sla_deadline THEN 1 ELSE 0 END)
        FROM ticket
        WHERE id_current_technician = :id
          AND close_date >= :startDate
          AND sla_deadline IS NOT NULL
        GROUP BY bucket
    """, nativeQuery = true)
    List<Object[]> countSlaRespectByPeriod(int id, LocalDateTime startDate, String unit);

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.graphs.SoftwareStatDto(
            s.name, COUNT(t))
        FROM Ticket t
        JOIN t.version v
        JOIN v.software s
        WHERE t.closeDate IS null
        GROUP BY s.name
    """)
    List<SoftwareStatDto> countTicketsBySoftware();

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.graphs.ThemeStatDto(
            th.designation, COUNT(t))
        FROM Theme th
        LEFT JOIN th.classificationList c
        LEFT JOIN c.ticket t
            ON t.closeDate IS NULL
        GROUP BY th.designation
    """)
    List<ThemeStatDto> countTicketsByTheme();

    // Admin dashboard
    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.currentStatus = 'CLOSED'
        AND t.closeDate IS null
    """)
    int closedTicketsWithoutEndDate();

    // Only CLOSED sets a close date: any other non-terminal status keeping one is inconsistent
    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.currentStatus NOT IN ('CLOSED', 'REJECTED')
        AND t.closeDate IS NOT null
    """)
    int openTicketsWithCloseDate();


    // Details des tickets
    @Query("""
        SELECT new com.mns.cda.suivimns.dto.details.TicketDetailDto(
            t.idTicket, t.title, t.initialPriority, t.currentPriority, t.currentStatus,
            t.overdue, t.slaDeadline,
            c.idAppUser, c.firstName, c.lastName, c.email, c.phoneNumber, c.importance,
            th.idTheme, th.code, th.designation,
            s.name, v.idVersion, CONCAT(v.versionNumber, ' ', vt.code), t.openDate, t.closeDate,
            t.description, t.solution,
            CONCAT(te.firstName, ' ', te.lastName), te.idAppUser, CONCAT(m.firstName, ' ', m.lastName), m.idAppUser, a.assignmentDate)
        FROM Ticket t
        JOIN t.client c
        JOIN t.version v
        JOIN v.versionType vt
        JOIN v.software s
        JOIN t.currentTheme th
        LEFT JOIN t.currentTechnician te
        LEFT JOIN t.currentManager m
        LEFT JOIN t.assignmentList a ON a.endDate IS null
        WHERE t.idTicket = :idTicket
    """)
    TicketDetailDto ticketDetail(Integer idTicket);


    @Query("""
        SELECT new com.mns.cda.suivimns.dto.details.TicketDetailKnowledge(
            k.idKnowledge, k.subject, k.description, k.resolution)
        FROM Ticket t
        JOIN t.knowledge k
        WHERE t.idTicket = :idTicket
    """)
    Optional<TicketDetailKnowledge> ticketKnowledge(Integer idTicket);


    @Query("""
        SELECT new com.mns.cda.suivimns.dto.details.TicketDetailProcedure(
            p.idProcedure, p.creationDate, p.modificationDate, p.title, p.content)
        FROM Ticket t
        JOIN t.knowledge k
        JOIN k.procedureList p
        WHERE t.idTicket = :idTicket
        ORDER BY p.creationDate DESC
    """)
    List<TicketDetailProcedure> ticketKnowledgeProcedures(Integer idTicket);



    @Query("""
        SELECT new com.mns.cda.suivimns.dto.details.TicketDetailComment(
            c.idComment, c.content, c.dateSent, c.lastModification,
            CONCAT(a.firstName, ' ', a.lastName),
            CASE
                WHEN TYPE(a) = Director THEN 'DIRECTOR'
                WHEN TYPE(a) = Client THEN 'CLIENT'
                WHEN TYPE(a) = Manager THEN 'MANAGER'
                WHEN TYPE(a) = Admin THEN 'ADMIN'
                WHEN TYPE(a) = Technician THEN 'TECHNICIAN'
                ELSE 'UNKNOWN'
            END, TREAT(a AS Technician).rank)
        FROM Comment c
        JOIN c.ticket t ON t.idTicket = :idTicket
        JOIN c.author a
    """)
    List<TicketDetailComment> ticketDetailComments(Integer idTicket);


    /* Deprecated queries
    @Query("SELECT new com.mns.cda.suivimns.dto.flat.TicketResponse(" +
            "t.idTicket, t.title, t.description, t.modificationDate, " +
            "t.currentPriority, t.version.versionNumber, t.version.versionType.designation, " +
            "t.version.software.name, t.client.firstName, t.client.lastName, " +
            "s.designation, th.designation) " +
            "FROM Ticket t " +
            "JOIN t.historyList h " +
            "JOIN h.status s " +
            "JOIN t.classificationList c " +
            "JOIN c.theme th " +
            "WHERE h.endDate IS NULL " +
            "AND c.affectationDate = (" +
            "SELECT MAX(cl2.affectationDate) " +
            "FROM Classification cl2 " +
            "WHERE cl2.ticket = t)")
    List<TicketResponse> findAllDto() ;

    @Query("SELECT new com.mns.cda.suivimns.dto.flat.TicketFullWithLatest(" +
            "t.idTicket, t.title, t.modificationDate, " +
            "t.currentPriority, v.versionNumber, vt.designation, " +
            "s.name, th.designation, st.designation, " +
            "COUNT(DISTINCT cm.idComment) AS commentCount) " +
            "FROM Ticket t " +
            "JOIN t.version v " +
            "JOIN v.versionType vt " +
            "JOIN v.software s " +
            "JOIN t.classificationList cl " +
            "JOIN cl.theme th " +
            "JOIN t.historyList h " +
            "JOIN h.status st " +
            "LEFT JOIN t.commentList cm " +
            "JOIN t.assignmentList a " +
            "JOIN a.technician tc " +
            "WHERE h.endDate IS NULL " +
            "AND tc.idAppUser = :id " +
            "AND cl.affectationDate = (" +
            "SELECT MAX(cl2.affectationDate) " +
            "FROM Classification cl2 " +
            "WHERE cl2.ticket = t)" +
            "GROUP BY t.idTicket, t.title, t.modificationDate, " +
            "t.currentPriority, v.versionNumber, vt.designation, " +
            "s.name, th.designation, st.designation")
    List<TicketFullWithLatest> returnTicketAttributed(@Param("id") int id);

    @Query("SELECT new com.mns.cda.suivimns.dto.flat.TicketFullWithLatest(" +
            "t.idTicket, t.title, t.modificationDate, " +
            "t.currentPriority, v.versionNumber, vt.designation, " +
            "s.name, th.designation, st.designation, " +
            "COUNT(DISTINCT cm.idComment) AS commentCount) " +
            "FROM Ticket t " +
            "JOIN t.version v " +
            "JOIN v.versionType vt " +
            "JOIN v.software s " +
            "JOIN t.classificationList cl " +
            "JOIN cl.theme th " +
            "JOIN t.historyList h " +
            "JOIN h.status st " +
            "LEFT JOIN t.commentList cm " +
            "WHERE h.endDate IS NULL " +
            "AND t.currentPriority >= 70 " +
            "AND cl.affectationDate = (" +
            "SELECT MAX(cl2.affectationDate) " +
            "FROM Classification cl2 " +
            "WHERE cl2.ticket = t)" +
            "GROUP BY t.idTicket, t.title, t.modificationDate, " +
            "t.currentPriority, v.versionNumber, vt.designation, " +
            "s.name, th.designation, st.designation ")
    List<TicketFullWithLatest> returnPriorityTicketFullWithLatest();

     */

}
