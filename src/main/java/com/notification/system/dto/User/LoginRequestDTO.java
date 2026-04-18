package com.notification.system.dto.User;

import com.notification.system.enums.Channel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;
    private String timezone;
    private Channel preferredChannel;
    private LocalDateTime createdAt;
}
