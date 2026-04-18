package com.notification.system.notification.dto;


import lombok.Data;

@Data
public class NotificationRequestDTO {

    private Long userId;
    private String message;
}
