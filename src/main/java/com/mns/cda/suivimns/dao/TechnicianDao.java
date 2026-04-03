package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianDao extends JpaRepository<Technician, Integer> {
}
