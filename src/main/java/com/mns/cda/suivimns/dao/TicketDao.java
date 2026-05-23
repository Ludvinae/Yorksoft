package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.dashboard.TicketStatusStatDto;
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

    // graphiques
    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.TicketStatusStatDto(
            t.currentStatus, COUNT(t), s.displayOrder)
        FROM Ticket t
        LEFT JOIN t.historyList h
        LEFT JOIN h.status s
        WHERE t.closeDate IS null
        AND t.currentStatus = s.code
        GROUP BY t.currentStatus, s.displayOrder
    """)
    List<TicketStatusStatDto> countTicketsByStatus();


    // Admin dashboard
    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.currentStatus = 'CLOSED'
        AND t.closeDate IS null
    """)
    int closedTicketsWithoutEndDate();


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
            "WHERE h.endDate IS NULL " +
            "AND cl.affectationDate = (" +
                "SELECT MAX(cl2.affectationDate) " +
                "FROM Classification cl2 " +
                "WHERE cl2.ticket = t)" +
            "GROUP BY t.idTicket, t.title, t.modificationDate, " +
                "t.currentPriority, v.versionNumber, vt.designation, " +
                "s.name, th.designation, st.designation")
    List<TicketFullWithLatest> returnTicketFullWithLatest();

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
