package com.mns.cda.suivimns.dto.dashboard.activity;

import java.time.LocalDateTime;

public record UserActivity(
        Integer idActivity,
        String activityDescription,
        LocalDateTime activityTimestamp,
        String activityTypeDesignation,
        String activityTypeName,
        String activityTypeIcon
) {
}
