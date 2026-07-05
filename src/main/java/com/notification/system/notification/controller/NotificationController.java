package com.notification.system.notification.controller;

import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<NotificationResponseDTO>> getAll(
            @PageableDefault(size = 20,sort = "createdAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        if(pageable.getPageSize()>100){
            throw new IllegalStateException("page size must not exceed 100");
        }
        return ResponseEntity.ok(notificationService.getAllNotifications(pageable));
    }

    @GetMapping("/status/{notificationStatus}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByStatus(@PathVariable NotificationStatus notificationStatus){
        return ResponseEntity.ok(notificationService.findNotificationByStatus(notificationStatus));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(notificationService.findNotificationByUser(userId));
    }
}
