package com.notification.system.notification.service;

import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.strategy.NotificationSender;
import com.notification.system.notification.strategy.NotificationSenderFactory;
import com.notification.system.user.entity.User;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.mapper.NotificationMapper;
import com.notification.system.notification.repository.NotificationRepository;
import com.notification.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.notification.system.user.enums.Channel.*;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationSenderFactory senderFactory;

    public boolean send(User user,Notification notification){
        NotificationSender sender= senderFactory.getSender(notification.getChannel());
        return Math.random()>0.3;
    }

    public NotificationResponseDTO create(NotificationRequestDTO requestDTO){
        User user=userRepository.findById(requestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Notification notification=notificationMapper.toEntity(requestDTO);
        notification.setUser(user);
        notification.setChannel(user.getPreferredChannel());
        notification.setRetryCount(0);
        notification.setNotificationStatus(NotificationStatus.PENDING);
        Notification saved=notificationRepository.save(notification);
        return processNotification(user,saved);
    }

    private NotificationResponseDTO processNotification(User user,Notification notification) {
        boolean sentOrNot=false;
        int retryCount=0;
        while (!sentOrNot && retryCount<3){
            notification.setNotificationStatus(NotificationStatus.RETRYING);
            sentOrNot=send(user,notification);
            retryCount++;
        }
        notification.setRetryCount(retryCount);
        notification.setNotificationStatus(
                sentOrNot ?
                        NotificationStatus.SENT
                        : NotificationStatus.FAILED);
        Notification updated = notificationRepository.save(notification);
        return notificationMapper.toResponse(updated);
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
