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
            "t.idTicket, t.title, t.openDate, t.closeDate, t.modificationDate, " +
            "t.description, t.callDuration, t.initialPriority, " +
            "t.finalPriority, v.versionNumber, vt.designation, " +
            "s.name, c.idAppUser, c.firstName, c.lastName, " +
            "th.designation, st.designation, cm.content, a.firstName, a.lastName) " +
            "FROM Ticket t " +
            "JOIN t.version v " +
            "JOIN v.versionType vt " +
            "JOIN v.software s " +
            "JOIN t.client c " +
            "JOIN t.classificationList cl " +
            "JOIN cl.theme th " +
            "JOIN t.historyList h " +
            "JOIN h.status st " +
            "JOIN t.commentList cm " +
            "JOIN cm.author a")
    List<TicketFullWithLatest> returnTicketFullWithLatest();



}
