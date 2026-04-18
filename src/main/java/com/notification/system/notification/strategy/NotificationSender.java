package com.notification.system.notification.strategy;

import com.notification.system.notification.entity.Notification;
import com.notification.system.user.entity.User;

public interface NotificationSender {
    public boolean send(User user, Notification notification);
}
