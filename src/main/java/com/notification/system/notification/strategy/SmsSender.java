package com.notification.system.notification.strategy;

import com.notification.system.notification.entity.Notification;
import com.notification.system.user.entity.User;
import com.notification.system.user.enums.Channel;
import org.springframework.stereotype.Service;

@Service
public class SmsSender implements NotificationSender{
    @Override
    public boolean sendNotification(User user, Notification notification) {
        System.out.println("Sending SMS");
        return true;
    }

    @Override
    public Channel getChannel() {
        return Channel.SMS;
    }
}
