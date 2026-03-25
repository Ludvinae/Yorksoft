package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDao extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmail(String email);
}
