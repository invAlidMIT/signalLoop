package com.notification.system.notification.kafka.exception;

public class NotificationDeliveryException extends RuntimeException {

    public NotificationDeliveryException(String message){
        super(message);
    }
}
