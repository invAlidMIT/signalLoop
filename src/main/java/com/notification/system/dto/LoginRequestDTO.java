package com.notification.system.dto;

import com.notification.system.enums.Channel;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;
    private String timezone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Channel preferredChannel;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
