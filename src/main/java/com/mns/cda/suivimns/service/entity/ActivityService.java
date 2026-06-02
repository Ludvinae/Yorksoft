package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.ActivityDao;
import com.mns.cda.suivimns.dto.dashboard.activity.UserActivity;
import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.model.Activity;
import com.mns.cda.suivimns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityDao activityDao;

    public void log(AppUser user, String description, ActivityType type) {
        Activity activity = new Activity();

        activity.setUser(user);
        activity.setDescription(description);
        activity.setActivityType(type);

        activityDao.save(activity);
    }

    public List<UserActivity> activityFeed(Integer userId) {
        return activityDao.activityFeed(userId);
    }

}
