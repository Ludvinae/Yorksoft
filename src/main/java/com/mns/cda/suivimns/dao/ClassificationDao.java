package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassificationDao extends JpaRepository<Classification, Integer> {

    @Query("""
        SELECT c FROM Classification c
        WHERE c.ticket.idTicket = :idTicket
        ORDER BY c.affectation_date DESC 
        LIMIT 1
        """)
    Optional<Classification> findLatestByTicket(@Param("idTicket") Integer idTicket);


    Optional<Classification> findById(ClassificationKey id);
}
