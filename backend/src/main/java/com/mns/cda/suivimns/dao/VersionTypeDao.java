package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.VersionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionTypeDao extends JpaRepository<VersionType, Integer> {
}
