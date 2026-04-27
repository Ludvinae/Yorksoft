package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.flat.TicketResponse;
import com.mns.cda.suivimns.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Integer> {
    @Query("SELECT new com.mns.cda.suivimns.dto.flat.TicketResponse(" +
            "t.idTicket, t.title, t.description, t.modificationDate, " +
            "t.finalPriority, t.version.versionNumber, t.version.versionType.designation, " +
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
                "t.finalPriority, v.versionNumber, vt.designation, " +
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
                "t.finalPriority, v.versionNumber, vt.designation, " +
                "s.name, th.designation, st.designation")
    List<TicketFullWithLatest> returnTicketFullWithLatest();

    @Query("SELECT new com.mns.cda.suivimns.dto.flat.TicketFullWithLatest(" +
            "t.idTicket, t.title, t.modificationDate, " +
            "t.finalPriority, v.versionNumber, vt.designation, " +
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
            "t.finalPriority, v.versionNumber, vt.designation, " +
            "s.name, th.designation, st.designation")
    List<TicketFullWithLatest> returnTicketAttributed(@Param("id") int id);

    @Query("SELECT new com.mns.cda.suivimns.dto.flat.TicketFullWithLatest(" +
            "t.idTicket, t.title, t.modificationDate, " +
            "t.finalPriority, v.versionNumber, vt.designation, " +
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
            "AND t.finalPriority <= 2 " +
            "AND cl.affectationDate = (" +
            "SELECT MAX(cl2.affectationDate) " +
            "FROM Classification cl2 " +
            "WHERE cl2.ticket = t)" +
            "GROUP BY t.idTicket, t.title, t.modificationDate, " +
            "t.finalPriority, v.versionNumber, vt.designation, " +
            "s.name, th.designation, st.designation ")
    List<TicketFullWithLatest> returnPriorityTicketFullWithLatest();

}
