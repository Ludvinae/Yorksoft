package com.mns.cda.suivimns.dto.dashboard.activity;

import java.time.LocalDateTime;

public record UserActivity(
        String actionDescription,
        String actionType,
        LocalDateTime actionTimestamp
) {
}
