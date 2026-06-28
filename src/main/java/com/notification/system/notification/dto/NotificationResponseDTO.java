package com.notification.system.notification.dto;

import com.notification.system.user.enums.Channel;
import com.notification.system.notification.enums.NotificationStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NotificationResponseDTO {

    private Long userId;
    private NotificationStatus notificationStatus;
    private LocalDateTime createdAt;
    private Channel channel;
    private String message;
    private int retryCount;
}
