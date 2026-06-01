package com.notification.system.notification.audit.controller;

import com.notification.system.notification.audit.dto.NotificationAuditDTO;
import com.notification.system.notification.audit.service.NotificationAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications/audit")
@RequiredArgsConstructor
public class NotificationAuditController {

    private final NotificationAuditService notificationAuditService;


    @GetMapping("/{notificationSelectionAuditId}")
    public ResponseEntity<NotificationAuditDTO> getNotificationAuditById(@PathVariable Long notificationSelectionAuditId){
        return ResponseEntity.ok(notificationAuditService.getNotificationSelectionAuditById(notificationSelectionAuditId));
    }
}
