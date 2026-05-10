package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentDao extends JpaRepository<Assignment, Integer> {

    @Query("""
        SELECT a FROM Assignment a
        WHERE a.ticket.idTicket = :idTicket
        AND a.endDate IS null
        """)
    Optional<Assignment> findLatestByTicket(@Param("idTicket") Integer idTicket);
}
