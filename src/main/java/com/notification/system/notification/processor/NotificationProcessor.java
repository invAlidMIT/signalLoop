package com.notification.system.notification.processor;

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
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationProcessor {

    private final NotificationRepository notificationRepository;
    private final NotificationSenderFactory senderFactory;
    private final NotificationMapper notificationMapper;

    public void processNotification(User user, Notification notification) {
        log.info("Processing notification {} on thread {}",
                notification.getNotificationId(),
                Thread.currentThread().getName());
        NotificationSender notificationSender = senderFactory.getSender(notification.getChannel());

        boolean success = notificationSender.sendNotification(user, notification);
        if (!success) {
            log.warn("Send failed for notification id={}, retrying...", notification.getNotificationId());
            notification.setRetryCount(notification.getRetryCount() + 1);
            notification.setNotificationStatus(NotificationStatus.RETRYING);
            notificationRepository.save(notification);
            throw new RuntimeException("Send failed");
        }
        notification.setNotificationStatus(NotificationStatus.SENT);
        notificationRepository.save(notification);
        log.info("Notification id={} sent successfully", notification.getNotificationId());
    }
}
