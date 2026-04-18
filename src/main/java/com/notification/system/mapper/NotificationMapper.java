package com.notification.system.mapper;

import com.notification.system.dto.Notification.NotificationRequestDTO;
import com.notification.system.dto.Notification.NotificationResponseDTO;
import com.notification.system.entity.Notification;
import com.notification.system.enums.NotificationStatus;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationRequestDTO requestDTO){
        Notification notification=new Notification(
                NotificationStatus.PENDING,
                requestDTO.getMessage()
        );
        return notification;
    }

    public NotificationResponseDTO toResponse(Notification notification){
        NotificationResponseDTO responseDTO=new NotificationResponseDTO(
                notification.getUser().getId(),
                notification.getNotificationStatus(),
                notification.getCreatedAt(),
                notification.getChannel(),
                notification.getMessage()
        );
        return responseDTO;
    }
}
