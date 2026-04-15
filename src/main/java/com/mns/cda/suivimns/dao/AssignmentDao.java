package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentDao extends JpaRepository<Assignment, Integer> {


}
