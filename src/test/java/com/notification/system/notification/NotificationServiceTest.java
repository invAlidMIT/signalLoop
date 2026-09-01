package com.notification.system.notification;

import com.notification.system.notification.audit.dto.NotificationAuditDTO;
import com.notification.system.notification.audit.mapper.NotificationAuditMapper;
import com.notification.system.notification.audit.service.NotificationAuditService;
import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.kafka.mapper.NotificationEventMapper;
import com.notification.system.notification.kafka.service.EventProducerService;
import com.notification.system.notification.mapper.NotificationMapper;
import com.notification.system.notification.repository.NotificationRepository;
import com.notification.system.notification.scoringAlogirthm.DefaultChannelScoringStrategy;
import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import com.notification.system.notification.scoringAlogirthm.service.ScoringConfigService;
import com.notification.system.notification.service.NotificationService;
import com.notification.system.user.entity.User;
import com.notification.system.user.enums.Channel;
import com.notification.system.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    @InjectMocks
    private NotificationService notificationService;


    // Common test data
    private NotificationRequestDTO request;
    private User user;
    private Notification notification;
    private Notification savedNotification;
    private NotificationResponseDTO response;
    private ScoresConfig scoresConfig;
    private NotificationAuditDTO auditDTO;


    @BeforeEach
    void setUp() {

        request = new NotificationRequestDTO();
        request.setUserId(1L);
        request.setUrgency(Urgency.HIGH);
        user = new User();
        user.setId(1L);
        notification = new Notification();
        savedNotification = new Notification();
        savedNotification.setNotificationId(100L);
        response = new NotificationResponseDTO();
        scoresConfig = new ScoresConfig();
        auditDTO = new NotificationAuditDTO();
        auditDTO.setSelectedChannel(Channel.SMS);
    }

    @Test
    void shouldCreateNotificationSuccessfully() {

        when(userRepository.findById(request.getUserId()))
                .thenReturn(Optional.of(user));
        when(notificationMapper.toEntity(request))
                .thenReturn(notification);
        when(scoringConfigService.loadScores())
                .thenReturn(scoresConfig);
        when(defaultChannelScoringStrategy.score(
                user, notification, Channel.SMS, scoresConfig))
                .thenReturn(10.0);
        when(defaultChannelScoringStrategy.score(
                user, notification, Channel.EMAIL, scoresConfig))
                .thenReturn(90.0);
        when(defaultChannelScoringStrategy.score(
                user, notification, Channel.PUSH, scoresConfig))
                .thenReturn(50.0);
        when(notificationRepository.save(notification))
                .thenReturn(savedNotification);
        when(notificationAuditMapper.toEntity(any(NotificationAuditDTO.class)))
                .thenReturn(null);
        when(notificationEventMapper.toNotificationEventDTO(savedNotification))
                .thenReturn(null);
        when(notificationMapper.toResponse(savedNotification))
                .thenReturn(response);

        NotificationResponseDTO result =
                notificationService.createNotification(request);

        assertNotNull(result);
        assertEquals(response, result);
        assertEquals(user, notification.getUser());
        assertEquals(Channel.EMAIL, notification.getChannel());
        assertEquals(0, notification.getRetryCount());
        assertEquals(
                NotificationStatus.PENDING,
                notification.getNotificationStatus()
        );

        verify(userRepository)
                .findById(request.getUserId());
        verify(notificationMapper)
                .toEntity(request);
        verify(scoringConfigService)
                .loadScores();
        verify(notificationRepository)
                .save(notification);
        verify(notificationMapper)
                .toResponse(savedNotification);
        verify(eventProducerService)
                .publish(any());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        when(userRepository.findById(request.getUserId()))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> notificationService.createNotification(request)
        );

        verify(userRepository)
                .findById(request.getUserId());
        verify(notificationMapper, never())
                .toEntity(any());
        verify(notificationRepository, never())
                .save(any());
        verify(eventProducerService, never())
                .publish(any());
    }
}