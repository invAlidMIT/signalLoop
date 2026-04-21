package com.notification.system.notification.service;

import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.mapper.NotificationMapper;
import com.notification.system.notification.repository.NotificationRepository;
import com.notification.system.notification.strategy.NotificationSender;
import com.notification.system.notification.strategy.NotificationSenderFactory;
import com.notification.system.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@EnableRetry
@Slf4j
@RequiredArgsConstructor
public class NotificationProcessor {

    private final NotificationRepository notificationRepository;
    private final NotificationSenderFactory senderFactory;
    private final NotificationMapper notificationMapper;

    @Async("AsyncNotificationExecutor")
    @Retryable(
            value = RuntimeException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 1000,multiplier = 2.0)
    )
        public void processNotification(User user, Notification notification){
        log.info("Processing notification {} on thread {}",
                notification.getId(),
                Thread.currentThread().getName());
        NotificationSender notificationSender= senderFactory.getSender(notification.getChannel());

            boolean success=notificationSender.send(user,notification);
            if(!success){
                log.warn("Send failed for notification id={}, retrying...", notification.getId());
                notification.setRetryCount(notification.getRetryCount()+1);
                notification.setNotificationStatus(NotificationStatus.RETRYING);
                notificationRepository.save(notification);
                throw new RuntimeException("Send failed");
            }
            notification.setNotificationStatus(NotificationStatus.SENT);
            notificationRepository.save(notification);
            log.info("Notification id={} sent successfully", notification.getId());
        }

        @Recover
        public NotificationResponseDTO recover(RuntimeException e,User user,Notification notification){
            log.error("Notification id={} failed after retries", notification.getId());
            notification.setNotificationStatus(NotificationStatus.FAILED);
            Notification saved=notificationRepository.save(notification);
            return notificationMapper.toResponse(saved);
        }
}
