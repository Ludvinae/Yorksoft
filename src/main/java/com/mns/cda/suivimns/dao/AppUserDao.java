package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserDao extends JpaRepository<AppUser, Integer> {
    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);
}
