package com.notification.system.notification.service;

import com.notification.system.notification.audit.dto.NotificationAuditDTO;
import com.notification.system.notification.audit.mapper.NotificationAuditMapper;
import com.notification.system.notification.audit.service.NotificationAuditService;
import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.exception.NotificationNotFoundException;
import com.notification.system.notification.kafka.dto.NotificationEventDTO;
import com.notification.system.notification.kafka.mapper.NotificationEventMapper;
import com.notification.system.notification.kafka.service.EventProducerService;
import com.notification.system.notification.mapper.NotificationMapper;
import com.notification.system.notification.repository.NotificationRepository;
import com.notification.system.notification.scoringAlogirthm.DefaultChannelScoringStrategy;
import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import com.notification.system.notification.scoringAlogirthm.service.ScoringConfigService;
import com.notification.system.user.entity.User;
import com.notification.system.user.enums.Channel;
import com.notification.system.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private ScoringConfigService scoringConfigService;

    @Mock
    private DefaultChannelScoringStrategy defaultChannelScoringStrategy;

    @Mock
    private EventProducerService eventProducerService;

    @Mock
    private NotificationEventMapper notificationEventMapper;

    @Mock
    private NotificationAuditService notificationAuditService;

    @Mock
    private NotificationAuditMapper notificationAuditMapper;

    @Spy
    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldCreateNotificationSuccessfully() {

        User user = new User();
        user.setId(1L);

        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setUserId(1L);
        request.setMessage("hello");
        request.setUrgency(Urgency.HIGH);

        Notification notification = new Notification();
        notification.setMessage("hello");

        Notification saved = new Notification();
        saved.setNotificationId(100L);
        saved.setChannel(Channel.EMAIL);

        NotificationAuditDTO audit = new NotificationAuditDTO();
        audit.setSelectedChannel(Channel.EMAIL);

        NotificationResponseDTO response =
                new NotificationResponseDTO(
                        1L,
                        NotificationStatus.PENDING,
                        null,
                        Channel.EMAIL,
                        "hello",
                        0
                );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(notificationMapper.toEntity(request))
                .thenReturn(notification);

        doReturn(audit)
                .when(notificationService)
                .getChannelFromScoreAlgorithm(user, notification);

        when(notificationRepository.save(notification))
                .thenReturn(saved);

        when(notificationAuditMapper.toEntity(any()))
                .thenReturn(null);

        when(notificationEventMapper.toNotificationEventDTO(saved))
                .thenReturn(new NotificationEventDTO());

        when(notificationMapper.toResponse(saved))
                .thenReturn(response);

        NotificationResponseDTO result =
                notificationService.createNotification(request);

        assertNotNull(result);

        assertEquals(Channel.EMAIL, notification.getChannel());

        assertEquals(NotificationStatus.PENDING,
                notification.getNotificationStatus());

        verify(notificationRepository).save(notification);

        verify(notificationAuditService)
                .createNotificationSelectionAudit(any());

        verify(eventProducerService)
                .publish(any(NotificationEventDTO.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setUserId(99L);

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> notificationService.createNotification(request)
        );

        verify(notificationRepository, never())
                .save(any());
    }
    @Test
    void shouldFindNotificationById() {

        Notification notification = new Notification();
        notification.setNotificationId(1L);

        NotificationResponseDTO response = new NotificationResponseDTO();

        when(notificationRepository.findById(1L))
                .thenReturn(Optional.of(notification));

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);

        NotificationResponseDTO result =
                notificationService.findNotificationById(1L);

        assertNotNull(result);

        verify(notificationRepository).findById(1L);
        verify(notificationMapper).toResponse(notification);
    }

    @Test
    void shouldThrowWhenNotificationNotFound() {

        when(notificationRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotificationNotFoundException.class,
                () -> notificationService.findNotificationById(1L)
        );
    }

    @Test
    void shouldReturnAllNotifications() {

        Notification n1 = new Notification();
        Notification n2 = new Notification();

        NotificationResponseDTO r1 = new NotificationResponseDTO();
        NotificationResponseDTO r2 = new NotificationResponseDTO();

        when(notificationRepository.findAll())
                .thenReturn(java.util.List.of(n1, n2));

        when(notificationMapper.toResponse(n1)).thenReturn(r1);
        when(notificationMapper.toResponse(n2)).thenReturn(r2);

        var result = notificationService.getAllNotifications();

        assertEquals(2, result.size());

        verify(notificationRepository).findAll();
    }

    @Test
    void shouldReturnNotificationsByStatus() {

        Notification notification = new Notification();

        NotificationResponseDTO response =
                new NotificationResponseDTO();

        when(notificationRepository.findByNotificationStatus(NotificationStatus.PENDING))
                .thenReturn(java.util.List.of(notification));

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);

        var result =
                notificationService.findNotificationByStatus(NotificationStatus.PENDING);

        assertEquals(1, result.size());

        verify(notificationRepository)
                .findByNotificationStatus(NotificationStatus.PENDING);
    }

    @Test
    void shouldReturnNotificationsByUser() {

        User user = new User();
        user.setId(1L);

        Notification notification = new Notification();

        NotificationResponseDTO response =
                new NotificationResponseDTO();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(notificationRepository.findByUser(user))
                .thenReturn(java.util.List.of(notification));

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);

        var result =
                notificationService.findNotificationByUser(1L);

        assertEquals(1, result.size());

        verify(notificationRepository).findByUser(user);
    }
    @Test
    void shouldThrowWhenUserNotFoundForFindNotificationByUser() {

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> notificationService.findNotificationByUser(99L)
        );

        verify(notificationRepository, never())
                .findByUser(any());
    }

    @Test
    void shouldSelectHighestScoringChannel() {

        User user = new User();

        Notification notification = new Notification();

        ScoresConfig config =
                new ScoresConfig(Map.of(), Map.of());

        when(scoringConfigService.loadScores())
                .thenReturn(config);

        when(defaultChannelScoringStrategy.score(user, notification, Channel.EMAIL, config))
                .thenReturn(90.0);

        when(defaultChannelScoringStrategy.score(user, notification, Channel.SMS, config))
                .thenReturn(70.0);

        when(defaultChannelScoringStrategy.score(user, notification, Channel.PUSH, config))
                .thenReturn(60.0);

        NotificationAuditDTO audit =
                notificationService.getChannelFromScoreAlgorithm(user, notification);

        assertEquals(Channel.EMAIL, audit.getSelectedChannel());

        assertEquals(90.0, audit.getEmailScore());

        assertEquals(70.0, audit.getSmsScore());

        assertEquals(60.0, audit.getPushScore());
    }

}
