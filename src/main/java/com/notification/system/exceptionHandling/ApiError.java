package com.notification.system.exceptionHandling;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;

}
