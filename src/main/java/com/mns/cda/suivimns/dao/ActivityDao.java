package com.mns.cda.suivimns.dao;

import com.mns.cda.suivimns.dto.dashboard.activity.UserActivity;
import com.mns.cda.suivimns.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityDao extends JpaRepository<Activity, Integer> {

    @Query("""
        SELECT new com.mns.cda.suivimns.dto.dashboard.activity.UserActivity(
            a.idActivity, a.description, a.timestamp
            )
        FROM Activity a
        ORDER BY a.timestamp DESC
        LIMIT 10
    """)
    List<UserActivity> activityFeed(Integer userId);
}
