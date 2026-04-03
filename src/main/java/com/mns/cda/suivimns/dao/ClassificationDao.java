package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationDao extends JpaRepository<Classification, Integer> {
}
