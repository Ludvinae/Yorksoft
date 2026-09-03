package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureDao extends JpaRepository<Procedure, Integer> {
}
