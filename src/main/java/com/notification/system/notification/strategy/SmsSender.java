package com.notification.system.notification.strategy;

import com.notification.system.notification.entity.Notification;
import com.notification.system.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class SmsSender implements NotificationSender{
    @Override
    public boolean sendNotification(User user, Notification notification) {
        System.out.println("Sending SMS");
        return true;
    }
}
