package com.campusactivity.service;

import com.campusactivity.entity.Activity;
import java.util.List;

public interface ActivityService {
    List<Activity> getAllActivities();
    Activity getActivityById(Long id);
    Activity createActivity(Activity activity);
    Activity updateActivity(Long id, Activity activity);
    void deleteActivity(Long id);

    // 新增方法
    String getActivityStatus(Long activityId, Long userId);
    boolean canUserRegister(Long activityId, Long userId);
    long getRegistrationCount(Long activityId);
}