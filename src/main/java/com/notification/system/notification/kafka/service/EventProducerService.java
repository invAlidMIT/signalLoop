package com.notification.system.notification.kafka.service;

import com.notification.system.notification.kafka.dto.NotificationEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducerService {

    private static final Logger log = LoggerFactory.getLogger(EventProducerService.class);
    private final KafkaTemplate<Long,NotificationEventDTO> kafkaTemplate;

    public void publish(NotificationEventDTO notificationEventDTO){

        try {
            kafkaTemplate.send(
                    "notification-events",
                    notificationEventDTO.getNotificationId(),
                    notificationEventDTO
            );

            log.info("Message published");
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("Kafka publish failed", e);
        }
    }

}
