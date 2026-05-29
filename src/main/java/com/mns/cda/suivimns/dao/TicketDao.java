package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.dashboard.graphs.SoftwareStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TechnicianWorkloadDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.ThemeStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TicketStatusStatDto;
import com.mns.cda.suivimns.dto.details.TicketDetailArticle;
import com.mns.cda.suivimns.dto.details.TicketDetailComment;
import com.mns.cda.suivimns.dto.details.TicketDetailDto;
import com.mns.cda.suivimns.dto.details.TicketDetailKnowledge;
import com.mns.cda.suivimns.enumerate.ThemeEnum;
import com.mns.cda.suivimns.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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


    @Query(value = """
        SELECT AVG(ticket_duration)
        FROM (
            SELECT 
                t.id_ticket,
                SUM(EXTRACT(EPOCH FROM (h.end_date - h.start_date))) AS ticket_duration
            FROM ticket t
            JOIN history h ON h.id_ticket = t.id_ticket
            WHERE t.id_technician = :id
              AND t.close_date >= :startDate
              AND h.status_code = 'IN_PROGRESS'
              AND h.end_date IS NOT NULL
            GROUP BY t.id_ticket
        ) durations
    """, nativeQuery = true)
    Double meanTimeToSolveTickets(int id, LocalDateTime startDate);

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
        FROM Ticket t
        LEFT JOIN t.historyList h
        LEFT JOIN h.status s
        WHERE t.closeDate IS null
        AND t.currentStatus = s.code
        GROUP BY t.currentStatus, s.displayOrder
    """)
    List<TicketStatusStatDto> countTicketsByStatus();

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


    // Details des tickets
    @Query("""
        SELECT new com.mns.cda.suivimns.dto.details.TicketDetailDto(
            t.idTicket, t.title, t.initialPriority, t.currentPriority, t.currentStatus,
            c.idAppUser, CONCAT(c.firstName, ' ', c.lastName), c.email, c.phoneNumber, c.importance,
            t.currentTheme, s.name, v.idVersion, CONCAT(v.versionNumber, ' ', vt.code), t.openDate, t.closeDate,
            t.description,
            CONCAT(te.firstName, ' ', te.lastName), te.idAppUser, CONCAT(m.firstName, ' ', m.lastName), m.idAppUser, a.assignmentDate)
        FROM Ticket t
        JOIN t.client c
        JOIN t.version v
        JOIN v.versionType vt
        JOIN v.software s
        LEFT JOIN t.currentTechnician te
        LEFT JOIN t.currentManager m
        LEFT JOIN t.assignmentList a ON a.endDate IS null
        WHERE t.idTicket = :idTicket
    """)
    TicketDetailDto ticketDetail(Integer idTicket);


    @Query("""
        SELECT new com.mns.cda.suivimns.dto.details.TicketDetailKnowledge(
            k.idKnowledge, k.subject)
        FROM Knowledge k
        JOIN k.versionList v ON v.idVersion = :idVersion
        WHERE k.theme.code = :themeEnum
    """)
    TicketDetailKnowledge ticketKnowledge(ThemeEnum themeEnum, Integer idVersion);


    @Query("""
        SELECT new com.mns.cda.suivimns.dto.details.TicketDetailArticle(
            a.idArticle, a.creationDate, a.modificationDate, a.title, a.content)
        FROM Article a
        JOIN a.knowledge k
        WHERE k.idKnowledge = :idKnowledge
    """)
    List<TicketDetailArticle>  ticketDetailArticles(Integer idKnowledge);

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.details.TicketDetailComment(
            c.idComment, c.content, c.dateSent, c.lastModification,
            CONCAT(a.firstName, ' ', a.lastName))
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
