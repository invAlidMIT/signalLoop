package com.notification.system.notification.kafka.service;

import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.exception.NotificationNotFoundException;
import com.notification.system.notification.kafka.dto.NotificationEventDTO;
import com.notification.system.notification.kafka.mapper.NotificationEventMapper;
import com.notification.system.notification.mapper.NotificationMapper;
import com.notification.system.notification.processor.NotificationProcessor;
import com.notification.system.notification.repository.NotificationRepository;
import com.notification.system.notification.service.NotificationService;
import com.notification.system.user.entity.User;
import com.notification.system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventConsumer.class);
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationProcessor notificationProcessor;
    private final NotificationMapper notificationMapper;

    @RetryableTopic(attempts = "4",
    backOff = @BackOff(delay = 2000,multiplier = 1.5,maxDelay = 10000))
    @KafkaListener(
            topics = "notification-events",
            groupId = "notification-group"
    )
    public void consume(NotificationEventDTO notificationEventDTO){
        log.info("consumed: "+notificationEventDTO.getNotificationId());
        Notification notification=notificationRepository.
                findByIdWithUser(notificationEventDTO.getNotificationId())
                .orElseThrow(()->new NotificationNotFoundException("Notification not found"));
        User user=notification.getUser();
        if(notification.getNotificationStatus().
                equals(NotificationStatus.SENT)){
            return;
        }
        notificationProcessor.processNotification(user,notification);
    }

    @DltHandler
    public void ListenDLT(NotificationEventDTO notificationEventDTO){
        log.error("DLT received: "+"\n notification event id: "+
                notificationEventDTO.getNotificationId());
    }
}
