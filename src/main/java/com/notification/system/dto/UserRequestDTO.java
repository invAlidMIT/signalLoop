package com.notification.system.dto;

import com.notification.system.enums.Channel;
import lombok.Getter;

@Getter
public class UserRequestDTO {

    private String email;
    private String password;
    private Channel preferredChannel;
    private String timezone;
}
