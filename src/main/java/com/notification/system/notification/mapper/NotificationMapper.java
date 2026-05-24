package com.notification.system.notification.mapper;

import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationRequestDTO requestDTO){
        Notification notification=new Notification(
                requestDTO.getMessage(),
                requestDTO.getUrgency()
        );
        return notification;
    }

    public NotificationResponseDTO toResponse(Notification notification){
        Long userId=notification.getUser()!=null ? notification.getUser().getId():null;
        NotificationResponseDTO responseDTO=new NotificationResponseDTO(
                userId,
                notification.getNotificationStatus(),
                notification.getCreatedAt(),
                notification.getChannel(),
                notification.getMessage(),
                notification.getRetryCount()
        );
        return responseDTO;
    }
}
