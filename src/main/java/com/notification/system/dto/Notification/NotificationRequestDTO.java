package com.notification.system.dto.Notification;


import com.notification.system.enums.Channel;
import com.notification.system.enums.NotificationStatus;
import lombok.Data;

@Data
public class NotificationRequestDTO {

    private Long userId;
    private String message;
}
