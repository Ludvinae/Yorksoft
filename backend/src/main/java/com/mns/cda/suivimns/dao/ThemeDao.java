package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeDao extends JpaRepository<Theme, Integer> {

    Optional<Theme> findByDesignation(String designation);

    Optional<Theme> findByCode(String code);
}
