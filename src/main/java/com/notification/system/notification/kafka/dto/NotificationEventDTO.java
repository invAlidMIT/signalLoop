package com.notification.system.notification.kafka.dto;

import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEventDTO {

    private Long notificationId;
    private Long userId;
    private String message;
    private Urgency urgency;

}
