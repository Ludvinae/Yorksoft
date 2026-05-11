package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryDao extends JpaRepository<History, Integer> {

    @Query("""
        SELECT h FROM History h
        WHERE h.ticket.idTicket = :idTicket
        AND h.endDate IS null
        """)
    Optional<History> findLatestByTicket(@Param("idTicket") Integer idTicket);

    // Equivalent a la query findLatestByTicket
    Optional<History> findByTicketIdTicketAndEndDateIsNull(Integer idTicket);

    List<History> findAllByTicketIdTicket(Integer idTicket);

    @Query("""
            SELECT h
            FROM History h
            WHERE h.ticket.idTicket = :idTicket
            AND h.status.code IN :statuses
    """)
    List<History> findAllActiveByIdTicket(@Param("idTicket") Integer idTicket, @Param("statuses") List<StatusEnum> statuses);
}
