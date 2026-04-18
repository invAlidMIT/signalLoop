package com.notification.system.dto.Notification;

import com.notification.system.entity.User;
import com.notification.system.enums.Channel;
import com.notification.system.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class NotificationResponseDTO {

    private Long userId;
    private NotificationStatus notificationStatus;
    private LocalDateTime createdAt;
    private Channel channel;
    private String message;
}
