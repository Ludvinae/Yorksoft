package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnicianDao extends JpaRepository<Technician, Integer> {

    Optional<Technician> findByEmail(String email);
}
