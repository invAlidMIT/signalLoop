package com.notification.system.user.dto;

import com.notification.system.user.enums.Channel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String email;
    private Channel preferredChannel;
    private String timezone;
    private LocalDateTime createdAt;
}
