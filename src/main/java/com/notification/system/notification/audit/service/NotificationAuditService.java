package com.notification.system.notification.audit.service;

import com.notification.system.notification.audit.dto.NotificationAuditDTO;
import com.notification.system.notification.audit.entity.NotificationAudit;
import com.notification.system.notification.audit.mapper.NotificationAuditMapper;
import com.notification.system.notification.audit.repository.NotificationAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationAuditService {

    private final NotificationAuditRepository notificationAuditRepository;
    private final NotificationAuditMapper notificationAuditMapper;

    public NotificationAuditDTO getNotificationSelectionAuditById(Long notificationSelectionAuditId){
        return notificationAuditMapper.toResponseDTO(notificationAuditRepository.findByNotificationSelectionAuditId(notificationSelectionAuditId).
                orElseThrow(()->new RuntimeException("Audit Not Found")));
    }

    @Transactional
    public void createNotificationSelectionAudit(NotificationAudit notificationAudit){
        notificationAuditRepository.save(notificationAudit);
    }

}
