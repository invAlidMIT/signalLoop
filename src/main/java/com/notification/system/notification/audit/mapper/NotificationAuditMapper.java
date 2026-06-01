package com.notification.system.notification.audit.mapper;

import com.notification.system.notification.audit.dto.NotificationAuditDTO;
import com.notification.system.notification.audit.entity.NotificationAudit;
import org.springframework.stereotype.Component;

@Component
public class NotificationAuditMapper {

    public NotificationAuditDTO toResponseDTO(
            NotificationAudit notificationAudit){
        NotificationAuditDTO notificationSelectionAuditResponseDTO=
                new NotificationAuditDTO();
        notificationSelectionAuditResponseDTO.setNotificationId(notificationAudit.getNotificationId());
        notificationSelectionAuditResponseDTO.setEmailScore(notificationAudit.getEmailScore());
        notificationSelectionAuditResponseDTO.setSmsScore(notificationAudit.getSmsScore());
        notificationSelectionAuditResponseDTO.setPushScore(notificationAudit.getPushScore());
        notificationSelectionAuditResponseDTO.setSelectedChannel(notificationAudit.getSelectedChannel());
        notificationSelectionAuditResponseDTO.setCreatedAt(notificationAudit.getCreatedAt());
        return notificationSelectionAuditResponseDTO;
    }

    public NotificationAudit toEntity(
            NotificationAuditDTO notificationAuditDTO
    ){
        NotificationAudit notificationAudit =new NotificationAudit();
        notificationAudit.setNotificationId(notificationAuditDTO.getNotificationId());
        notificationAudit.setEmailScore(notificationAuditDTO.getEmailScore());
        notificationAudit.setSmsScore(notificationAuditDTO.getSmsScore());
        notificationAudit.setPushScore(notificationAuditDTO.getPushScore());
        notificationAudit.setSelectedChannel(notificationAuditDTO.getSelectedChannel());
        return notificationAudit;
    }

}
