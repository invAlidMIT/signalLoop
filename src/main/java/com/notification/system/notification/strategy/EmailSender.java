package com.notification.system.notification.strategy;

import com.notification.system.notification.entity.Notification;
import com.notification.system.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class EmailSender implements NotificationSender{
    @Override
    public boolean send(User user, Notification notification) {
        System.out.println("Sending to email: "+user.getEmail());
        return true;
    }
}
