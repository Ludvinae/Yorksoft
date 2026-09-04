package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.ActivityDao;
import com.mns.cda.suivimns.dao.ActivityTypeDao;
import com.mns.cda.suivimns.dto.dashboard.activity.UserActivity;
import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.model.Activity;
import com.mns.cda.suivimns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityDao activityDao;
    private final ActivityTypeDao activityTypeDao;

    public void log(AppUser user, String description, ActivityType type) {
        Activity activity = new Activity();

        activity.setUser(user);
        activity.setDescription(description);
        activity.setType(activityTypeDao.findByDesignation(type.name())
                .orElseThrow(() -> new IllegalStateException("No ActivityType row found for designation " + type.name())));

        activityDao.save(activity);
    }

    public List<UserActivity> activityFeed(Integer userId) {
        return activityDao.activityFeed(userId);
    }

}
