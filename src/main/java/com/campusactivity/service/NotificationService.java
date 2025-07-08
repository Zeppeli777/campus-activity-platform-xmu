package com.campusactivity.service;

import com.campusactivity.entity.Notification;
import com.campusactivity.entity.User;
import java.util.List;

public interface NotificationService {
    /**
     * 发送通知给用户
     * @param user 接收通知的用户
     * @param message 通知内容
     */
    void sendNotification(User user, String message);
    
    /**
     * 获取用户的所有通知
     * @param user 用户
     * @return 通知列表
     */
    List<Notification> getUserNotifications(User user);
    
    /**
     * 获取用户的未读通知
     * @param user 用户
     * @return 未读通知列表
     */
    List<Notification> getUserUnreadNotifications(User user);
    
    /**
     * 将通知标记为已读
     * @param notificationId 通知ID
     */
    void markAsRead(Long notificationId);
    
    /**
     * 获取用户未读通知数量
     * @param user 用户
     * @return 未读通知数量
     */
    long getUnreadNotificationCount(User user);
}