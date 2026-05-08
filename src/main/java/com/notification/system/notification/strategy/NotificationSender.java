package com.notification.system.notification.strategy;

import com.notification.system.notification.entity.Notification;
import com.notification.system.user.entity.User;
import com.notification.system.user.enums.Channel;

public interface NotificationSender {
    public boolean sendNotification(User user, Notification notification);
    public Channel getChannel();
}
