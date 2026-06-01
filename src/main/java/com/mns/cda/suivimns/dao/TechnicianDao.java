package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.dashboard.activity.UserActivity;
import com.mns.cda.suivimns.dto.flat.TechnicianWorkloadDetailedDto;
import com.mns.cda.suivimns.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicianDao extends JpaRepository<Technician, Integer> {

    Optional<Technician> findByEmail(String email);


    @Query("""
        SELECT new com.mns.cda.suivimns.dto.flat.TechnicianWorkloadDetailedDto(
            te.idAppUser, CONCAT(te.firstName, ' ', te.lastName), te.rank, COUNT(t),
            COALESCE(SUM(CASE WHEN t.currentPriority > 75 THEN 1 ELSE 0 END), 0))
        FROM Technician te
        LEFT JOIN te.assignmentList a
            ON a.endDate IS null
        LEFT JOIN a.ticket t
            ON t.closeDate IS null
        GROUP BY te.idAppUser, te.firstName, te.lastName, te.rank
        ORDER BY COUNT(t), te.rank ASC
    """)
    List<TechnicianWorkloadDetailedDto> getTechnicianWorkload();


/*
    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.activity.UserActivity(
            t.
        FROM Technician u
        JOIN u.assignmentList a
        JOIN a.ticket t
        WHERE u.idAppUser = :id
    """)
    List<UserActivity> getTechnicianAssignmentActivity(int id);


    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.activity.UserActivity(
            )
        FROM Technician u
        JOIN u.articleList a
        WHERE u.idAppUser = :id
    """)
    List<UserActivity> getTechnicianArticleActivity(int id);

 */
}
