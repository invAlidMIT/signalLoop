package com.notification.system.notification.audit.dto;

import com.notification.system.user.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAuditDTO {
    private Long notificationId;
    private double emailScore;
    private double smsScore;
    private double pushScore;
    private Channel selectedChannel;
    private LocalDateTime createdAt;
}
