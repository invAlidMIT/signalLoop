package com.notification.system.dto.User;

import com.notification.system.enums.Channel;
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
