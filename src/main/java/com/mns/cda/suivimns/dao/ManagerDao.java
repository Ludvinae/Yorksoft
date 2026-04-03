package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerDao extends JpaRepository<Manager, Integer> {
}
