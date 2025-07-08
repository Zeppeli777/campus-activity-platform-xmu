package com.campusactivity.controller.admin;

import com.campusactivity.entity.Activity;
import com.campusactivity.entity.Registration;
import com.campusactivity.entity.User;
import com.campusactivity.service.ActivityService;
import com.campusactivity.service.NotificationService;
import com.campusactivity.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/activities")
public class AdminActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable Long id) {
        return activityService.getActivityById(id);
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        Activity oldActivity = activityService.getActivityById(id);
        Activity updatedActivity = activityService.updateActivity(id, activity);
        
        // 获取该活动的所有报名用户
        List<Registration> registrations = registrationService.getRegistrationsByActivityId(id);
        
        // 发送编辑通知
        String message = String.format("活动 '%s' 已被更新，请查看最新信息。", oldActivity.getTitle());
        for (Registration registration : registrations) {
            User user = registration.getUser();
            notificationService.sendNotification(user, message);
        }
        
        return updatedActivity;
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable Long id) {
        // 获取活动信息
        Activity activity = activityService.getActivityById(id);
        String activityTitle = activity.getTitle();
        
        // 获取该活动的所有报名用户
        List<Registration> registrations = registrationService.getRegistrationsByActivityId(id);
        
        // 先删除活动
        activityService.deleteActivity(id);
        
        // 发送删除通知
        String message = String.format("活动 '%s' 已被删除。", activityTitle);
        for (Registration registration : registrations) {
            User user = registration.getUser();
            notificationService.sendNotification(user, message);
        }
    }
}