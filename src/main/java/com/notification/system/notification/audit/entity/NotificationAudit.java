package com.notification.system.notification.audit.entity;

import com.notification.system.user.enums.Channel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_selection_audit")
@Getter
@Setter
public class NotificationAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationSelectionAuditId;

    private Long notificationId;
    private double emailScore;
    private double smsScore;
    private double pushScore;
    @Enumerated(value = EnumType.STRING)
    private Channel selectedChannel;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
