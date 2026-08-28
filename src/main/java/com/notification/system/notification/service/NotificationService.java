package com.notification.system.notification.service;

import com.notification.system.notification.audit.dto.NotificationAuditDTO;
import com.notification.system.notification.audit.mapper.NotificationAuditMapper;
import com.notification.system.notification.audit.service.NotificationAuditService;
import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.exception.NotificationNotFoundException;
import com.notification.system.notification.kafka.mapper.NotificationEventMapper;
import com.notification.system.notification.kafka.service.EventProducerService;
import com.notification.system.notification.mapper.NotificationMapper;
import com.notification.system.notification.repository.NotificationRepository;
import com.notification.system.notification.scoringAlogirthm.DefaultChannelScoringStrategy;
import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import com.notification.system.notification.scoringAlogirthm.service.ScoringConfigService;
import com.notification.system.user.entity.User;
import com.notification.system.user.enums.Channel;
import com.notification.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final ScoringConfigService scoringConfigService;
    private final DefaultChannelScoringStrategy defaultChannelScoringStrategy;
    private final EventProducerService eventProducerService;
    private final NotificationEventMapper notificationEventMapper;
    private final NotificationAuditService notificationAuditService;
    private final NotificationAuditMapper notificationAuditMapper;


    @Transactional
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Notification notification = notificationMapper.toEntity(requestDTO);
        NotificationAuditDTO notificationAuditDTO =getChannelFromScoreAlgorithm(user,notification);
        notification.setUser(user);
        notification.setChannel(notificationAuditDTO.getSelectedChannel());
        notification.setRetryCount(0);
        notification.setNotificationStatus(NotificationStatus.PENDING);
        Notification saved = notificationRepository.save(notification);
        log.info("Notification created. notificationId={}, userId={}, channel={}",
                saved.getNotificationId(),
                user.getId(),
                saved.getChannel()
                );
        notificationAuditDTO.setNotificationId(saved.getNotificationId());
        notificationAuditService.createNotificationSelectionAudit(notificationAuditMapper.toEntity(notificationAuditDTO));
        eventProducerService.publish(notificationEventMapper.toNotificationEventDTO(saved));
        return notificationMapper.toResponse(saved);
    }

    public NotificationResponseDTO findNotificationById(Long id) {
        return notificationMapper.toResponse(notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException("notification not found")));
    }

    public Page<NotificationResponseDTO> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable)
                .map(notificationMapper::toResponse);
    }

    public List<NotificationResponseDTO> findNotificationByStatus(NotificationStatus status) {
        return notificationRepository.findByNotificationStatus(status)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    public List<NotificationResponseDTO> findNotificationByUser(Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return notificationRepository.findByUser(user)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    public NotificationAuditDTO getChannelFromScoreAlgorithm(User user, Notification notification){
        Map<Channel,Double> channelScoreMap=new HashMap<>();
        ScoresConfig scoresConfig=scoringConfigService.loadScores();
        for (Channel channel:Channel.values())
        channelScoreMap.put(channel,defaultChannelScoringStrategy.score(user,notification,channel,scoresConfig));

        NotificationAuditDTO notificationAuditDTO =new NotificationAuditDTO();
        notificationAuditDTO.setSmsScore(channelScoreMap.get(Channel.SMS));
        notificationAuditDTO.setEmailScore(channelScoreMap.get(Channel.EMAIL));
        notificationAuditDTO.setPushScore(channelScoreMap.get(Channel.PUSH));
        Channel selectedChannel=channelScoreMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Channel.SMS);

        notificationAuditDTO.setSelectedChannel(selectedChannel);
        return notificationAuditDTO;



    }
}
