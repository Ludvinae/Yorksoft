package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorDao extends JpaRepository<Director, Integer> {

    Optional<Director> findByEmail(String email);
}
