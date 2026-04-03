package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorDao extends JpaRepository<Director, Integer> {
}
