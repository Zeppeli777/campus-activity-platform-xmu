package com.campusactivity.controller.user;

import com.campusactivity.entity.Notification;
import com.campusactivity.entity.User;
import com.campusactivity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     * 获取当前用户的所有通知
     */
    @GetMapping
    public List<Notification> getUserNotifications(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return notificationService.getUserNotifications(user);
    }

    /**
     * 获取当前用户的未读通知
     */
    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return notificationService.getUserUnreadNotifications(user);
    }

    /**
     * 将通知标记为已读
     */
    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }

    /**
     * 获取未读通知数量
     */
    @GetMapping("/unread/count")
    public Map<String, Long> getUnreadCount(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        long count = notificationService.getUnreadNotificationCount(user);
        Map<String, Long> result = new HashMap<>();
        result.put("count", count);
        return result;
    }
}