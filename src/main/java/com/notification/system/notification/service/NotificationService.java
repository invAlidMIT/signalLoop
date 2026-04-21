package com.notification.system.notification.service;

import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import com.notification.system.user.entity.User;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.mapper.NotificationMapper;
import com.notification.system.notification.repository.NotificationRepository;
import com.notification.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationProcessor notificationProcessor;


    public NotificationResponseDTO create(NotificationRequestDTO requestDTO){
        User user=userRepository.findById(requestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Notification notification=notificationMapper.toEntity(requestDTO);
        notification.setUser(user);
        notification.setChannel(user.getPreferredChannel());
        notification.setRetryCount(0);
        notification.setNotificationStatus(NotificationStatus.PENDING);
        Notification saved=notificationRepository.save(notification);
        return notificationProcessor.processNotification(user,saved);
    }

    public NotificationResponseDTO findById(Long id){
        return notificationMapper.toResponse(notificationRepository.findById(id).orElseThrow(()-> new RuntimeException("Notification not found")));
    }

    public List<NotificationResponseDTO> getAll(){
        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }
}
