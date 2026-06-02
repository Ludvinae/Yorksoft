package com.mns.cda.suivimns.dto.dashboard.activity;

import java.time.LocalDateTime;

public record UserActivity(
        String activityDescription,
        //String activityType,
        LocalDateTime activityTimestamp
) {
}
