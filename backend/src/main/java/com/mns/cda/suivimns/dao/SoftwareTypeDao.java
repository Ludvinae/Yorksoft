package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.SoftwareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareTypeDao extends JpaRepository<SoftwareType, Integer> {
}
