package com.notification.system.notification.entity;

import com.notification.system.user.entity.User;
import com.notification.system.user.enums.Channel;
import com.notification.system.notification.enums.NotificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus notificationStatus;

    private int retryCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Channel channel;

    @NotBlank
    private String message;

    public Notification(
                        String message){
        this.message=message;
    }
}
