package com.notification.system.notification.controller;

import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.exception.NotificationNotFoundException;
import com.notification.system.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> sendNotification(@Valid @RequestBody NotificationRequestDTO requestDTO){
        return ResponseEntity.ok(notificationService.createNotification(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.findNotificationById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAll(){
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/status/{notificationStatus}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationByStatus(@PathVariable NotificationStatus notificationStatus){
        return ResponseEntity.ok(notificationService.findNotificationByStatus(notificationStatus));
    }
}
