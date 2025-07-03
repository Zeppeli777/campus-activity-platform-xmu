package com.campusactivity.service.impl;

import com.campusactivity.entity.Activity;
import com.campusactivity.repository.ActivityRepository;
import com.campusactivity.repository.RegistrationRepository;
import com.campusactivity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    @Override
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Long id, Activity activity) {
        if (activityRepository.existsById(id)) {
            activity.setId(id);
            return activityRepository.save(activity);
        }
        return null;
    }

    @Override
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public String getActivityStatus(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (activity == null) {
            return "未知";
        }

        Date now = new Date();

        // 检查活动是否已结束
        if (activity.getEndTime() != null && activity.getEndTime().before(now)) {
            return "已结束";
        }

        // 检查用户是否已报名
        if (userId != null) {
            Long registrationCount = registrationRepository.countByActivityIdAndUserId(activityId, userId);
            if (registrationCount > 0) {
                return "已报名";
            }
        }

        // 检查是否满额
        Long currentRegistrations = registrationRepository.countByActivityId(activityId);
        if (activity.getCapacity() != null && currentRegistrations >= activity.getCapacity()) {
            return "已满额";
        }

        return "可报名";
    }

    @Override
    public boolean canUserRegister(Long activityId, Long userId) {
        return "可报名".equals(getActivityStatus(activityId, userId));
    }

    @Override
    public long getRegistrationCount(Long activityId) {
        return registrationRepository.countByActivityId(activityId);
    }
}