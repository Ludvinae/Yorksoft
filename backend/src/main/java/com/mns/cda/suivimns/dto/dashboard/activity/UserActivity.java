package com.mns.cda.suivimns.dto.dashboard.activity;

import com.mns.cda.suivimns.enumerate.ActivityType;

import java.time.LocalDateTime;

public record UserActivity(
        Integer idActivity,
        String activityDescription,
        LocalDateTime activityTimestamp,
        ActivityType activityType
) {
}
