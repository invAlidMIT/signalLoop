package com.notification.system.dto;

import com.notification.system.enums.Channel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequestDTO {

    private String email;
    private String password;
    private Channel preferredChannel;
    private String timezone;
}
