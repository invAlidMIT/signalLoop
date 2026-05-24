package com.notification.system.notification.dto;


import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class NotificationRequestDTO {

    private Long userId;
    private String message;

    @Enumerated(EnumType.STRING)
    private Urgency urgency;
}
