package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Admin;
import com.mns.cda.suivimns.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByEmail(String email);
}
