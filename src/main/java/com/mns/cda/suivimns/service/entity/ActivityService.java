package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.ActivityDao;
import com.mns.cda.suivimns.model.Activity;
import com.mns.cda.suivimns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityDao activityDao;

    public void log(AppUser user, String description) {
        Activity activity = new Activity();

        activity.setUser(user);
        activity.setDescription(description);

        activityDao.save(activity);
    }


}
