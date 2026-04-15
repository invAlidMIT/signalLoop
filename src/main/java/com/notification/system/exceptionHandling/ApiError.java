package com.notification.system.exceptionHandling;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiError {
    private HttpStatus errorCode;
    private String errorMessage;
    private LocalDateTime timeStamp;

    public ApiError(){
        this.timeStamp=LocalDateTime.now();
    }

    public ApiError(String errorMessage,HttpStatus errorCode){
        this();
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }
}
