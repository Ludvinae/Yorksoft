package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmail(String email);
}
