package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseDao extends JpaRepository<License, Integer> {
}
