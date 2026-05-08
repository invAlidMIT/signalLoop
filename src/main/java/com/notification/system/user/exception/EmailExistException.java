package com.notification.system.user.exception;

public class EmailExistException extends RuntimeException{
    public EmailExistException(String message){
        super(message);
    }
}
