package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryDao extends JpaRepository<History, Integer> {

    @Query("""
        SELECT h FROM History h
        WHERE h.ticket.idTicket = :ticketId
        AND h.endDate = NULL 
        ORDER BY h.startDate DESC
        """)
    Optional<History> findLatestByTicket(@Param("ticketId") Integer ticketId);
}
