package com.notification.system.user.dto;

import com.notification.system.user.enums.Channel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String email;
    private String password;
    private Channel preferredChannel;
    private String timezone;
}
