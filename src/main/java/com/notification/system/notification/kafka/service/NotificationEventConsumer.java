package com.notification.system.notification.kafka.service;

import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.exception.NotificationNotFoundException;
import com.notification.system.notification.kafka.dto.NotificationEventDTO;
import com.notification.system.notification.processor.NotificationProcessor;
import com.notification.system.notification.repository.NotificationRepository;
import com.notification.system.user.entity.User;
import com.notification.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventConsumer.class);
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationProcessor notificationProcessor;

    @KafkaListener(
            topics = "notification-events",
            groupId = "notification-group"
    )
    public void Consume(NotificationEventDTO notificationEventDTO){
        log.info(notificationEventDTO.getMessage());
        Notification notification=notificationRepository.
                findById(notificationEventDTO.getNotificationId()).
                orElseThrow(()->new NotificationNotFoundException("Notification not found!"));
        User user=userRepository.findById(notificationEventDTO.getUserId())
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
        if(notification.getNotificationStatus().
                equals(NotificationStatus.SENT)){
            return;
        }
        notificationProcessor.processNotification(user,notification);
    }
}
