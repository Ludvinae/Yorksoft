package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserDao extends JpaRepository<AppUser, Integer> {
    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);

    // Native on purpose: the role is the subtype table a user appears in, and JPQL only sees one subtype per row
    @Query(value = """
        SELECT COUNT(*)
        FROM app_user u
        WHERE NOT EXISTS (SELECT 1 FROM admin r WHERE r.id_app_user = u.id_app_user)
        AND NOT EXISTS (SELECT 1 FROM director r WHERE r.id_app_user = u.id_app_user)
        AND NOT EXISTS (SELECT 1 FROM manager r WHERE r.id_app_user = u.id_app_user)
        AND NOT EXISTS (SELECT 1 FROM technician r WHERE r.id_app_user = u.id_app_user)
        AND NOT EXISTS (SELECT 1 FROM client r WHERE r.id_app_user = u.id_app_user)
    """, nativeQuery = true)
    long countUsersWithoutRole();

    // A user in several role tables silently gets the first one AppUserDetailsService finds
    @Query(value = """
        SELECT COUNT(*)
        FROM (
            SELECT roles.id_app_user
            FROM (
                SELECT id_app_user FROM admin
                UNION ALL SELECT id_app_user FROM director
                UNION ALL SELECT id_app_user FROM manager
                UNION ALL SELECT id_app_user FROM technician
                UNION ALL SELECT id_app_user FROM client
            ) roles
            GROUP BY roles.id_app_user
            HAVING COUNT(*) > 1
        ) multi
    """, nativeQuery = true)
    long countUsersWithMultipleRoles();
}
