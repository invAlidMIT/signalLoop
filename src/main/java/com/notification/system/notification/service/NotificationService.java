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

import static com.notification.system.user.enums.Channel.*;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public NotificationResponseDTO send(NotificationRequestDTO requestDTO){
        User user=userRepository.findById(requestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Notification notification=notificationMapper.toEntity(requestDTO);
        notification.setUser(user);
        notification.setChannel(user.getPreferredChannel());
        notification.setRetryCount(0);
        Boolean sentOrNot=sendThroughChannel(notification);
        notification.setNotificationStatus(
                sentOrNot ?
                NotificationStatus.SENT
                : NotificationStatus.FAILED);
        Notification savedNotification=notificationRepository.save(notification);
        return notificationMapper.toResponse(savedNotification);
    }

    public boolean sendThroughChannel(Notification notification){
        switch (notification.getChannel()) {
            case SMS -> {
                System.out.println("Sending through SMS: " + notification.getMessage());
                return true;
            }
            case EMAIL -> {
                System.out.println("Sending through Email: " + notification.getMessage());
            return true;
        }
            case PUSH-> {
                System.out.println("Sending through PUSH: " + notification.getMessage());
                return true;
            }
            default -> {
                return false;
            }
        }
    }

}
