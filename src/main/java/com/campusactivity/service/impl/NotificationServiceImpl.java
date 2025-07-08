package com.campusactivity.service.impl;

import com.campusactivity.entity.Notification;
import com.campusactivity.entity.User;
import com.campusactivity.repository.NotificationRepository;
import com.campusactivity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Override
    public void sendNotification(User user, String message) {
        // 创建并保存通知
        Notification notification = new Notification(user, message);
        notificationRepository.save(notification);
        
        // 同时打印日志
        System.out.println("向用户 " + user.getUsername() + " 发送通知: " + message);
    }
    
    @Override
    public List<Notification> getUserNotifications(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    @Override
    public List<Notification> getUserUnreadNotifications(User user) {
        return notificationRepository.findByUserAndReadFalseOrderByCreatedAtDesc(user);
    }
    
    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new IllegalArgumentException("通知不存在: " + notificationId));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
    
    @Override
    public long getUnreadNotificationCount(User user) {
        return notificationRepository.countByUserAndReadFalse(user);
    }
}