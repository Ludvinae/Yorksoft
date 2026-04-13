package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Integer> {

    @Query("SELECT new com.mns.cda.suivimns.dto.TicketFullWithLatest(" +
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
            "AND cl.affectation_date = (" +
                "SELECT MAX(cl2.affectation_date) " +
                "FROM Classification cl2 " +
                "WHERE cl2.ticket = t)" +
            "GROUP BY t.idTicket, t.title, t.modificationDate, " +
                "t.finalPriority, v.versionNumber, vt.designation, " +
                "s.name, th.designation, st.designation")
    List<TicketFullWithLatest> returnTicketFullWithLatest();

    /*
    @Query("SELECT new com.mns.cda.suivimns.dto.TicketFullWithLatest(" +
            "t.idTicket, t.title, t.openDate, t.modificationDate, " +
            "t.description, t.callDuration, t.initialPriority, " +
            "t.finalPriority, v.versionNumber, vt.designation, " +
            "s.name, th.designation, st.designation, COUNT(DISTINCT cm.idComment) AS commentCount) " +
            "FROM Ticket t " +
            "JOIN t.version v " +
            "JOIN v.versionType vt " +
            "JOIN v.software s " +
            "JOIN t.classificationList cl " +
            "JOIN cl.theme th " +
            "JOIN t.historyList h " +
            "JOIN h.status st " +
            "LEFT JOIN t.commentList cm")
    List<TicketFullWithLatest> returnTicketFullWithLatest();

     */

/*
    @Query("FROM Ticket t1 WHERE t1.idTicket = (" +
            "SELECT t2.idTicket, max(t2.creationdate) FROM Ticket T2" +
            "GROUP BY Ticket t2.user)")

 */



}
