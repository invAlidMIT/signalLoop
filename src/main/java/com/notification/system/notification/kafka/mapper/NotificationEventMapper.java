package com.notification.system.notification.kafka.mapper;

import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.kafka.dto.NotificationEventDTO;
import com.notification.system.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventMapper {

    public NotificationEventDTO toNotificationEventDTO(Notification notification){

        NotificationEventDTO notificationEventDTO=new NotificationEventDTO();
        notificationEventDTO.setNotificationId(notification.getNotificationId());
        return notificationEventDTO;
    }
}
