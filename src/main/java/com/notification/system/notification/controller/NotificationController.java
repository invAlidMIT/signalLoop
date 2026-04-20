package com.notification.system.notification.controller;

import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
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
        return ResponseEntity.ok(notificationService.create(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(@PathVariable Long id){
        return ResponseEntity.ok(notificationService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAll(){
        return ResponseEntity.ok(notificationService.getAll());
    }
}
