package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Impact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpactDao extends JpaRepository<Impact, Integer> {
}
