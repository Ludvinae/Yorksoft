package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityTypeDao extends JpaRepository<ActivityType, Integer> {
    Optional<ActivityType> findByDesignation(String designation);
}
