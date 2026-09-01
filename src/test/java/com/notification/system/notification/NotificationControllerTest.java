package com.notification.system.notification;

import com.notification.system.auth.service.CustomUserDetailsService;
import com.notification.system.notification.controller.NotificationController;
import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.enums.NotificationStatus;
import com.notification.system.notification.exception.NotificationNotFoundException;
import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import com.notification.system.notification.service.NotificationService;
import com.notification.system.user.enums.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import com.notification.system.auth.JwtAuthUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @MockitoBean
    private NotificationService notificationService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtAuthUtil jwtAuthUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private NotificationRequestDTO request;
    private NotificationResponseDTO response;

    @BeforeEach
    void setup(){
        request=new NotificationRequestDTO();
        response=new NotificationResponseDTO();
        request.setUserId(1L);
        request.setMessage("vpn login detected");
        request.setUrgency(Urgency.MEDIUM);
        response.setUserId(1L);
        response.setMessage("VPN login detected");
        response.setChannel(Channel.EMAIL);
        response.setNotificationStatus(NotificationStatus.PENDING);
        response.setRetryCount(0);
    }

    @Test
    void shouldNotificationController() throws Exception {

        when(notificationService.createNotification((any())))
                .thenReturn(response);

        mockMvc.perform(
                post("/notifications")
                        .with(user("testuser"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.userId")
                                .value(1)
                );

        ArgumentCaptor<NotificationRequestDTO> captor=
                ArgumentCaptor.forClass(NotificationRequestDTO.class);
        verify(notificationService)
                .createNotification(captor.capture());
        NotificationRequestDTO capturedRequest=
                captor.getValue();

        assertEquals(request.getMessage(),capturedRequest.getMessage());
        assertEquals(
                request.getUrgency(),capturedRequest.getUrgency()
        );
    }

    @Test
    void shouldReturnBadRequestForInvalidRequest() throws Exception{
        NotificationRequestDTO invalidRequest=new NotificationRequestDTO();
        mockMvc.perform(
                post("/notifications")
                        .with(user("testuser"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(invalidRequest)
                        )
        )
                .andExpect(status().isBadRequest());

        verify(notificationService,never())
                .createNotification(any());
    }

    @Test
    void shouldReturnNotFoundWhenNotificationNotExist() throws Exception{
        when(notificationService.findNotificationById(100L))
                .thenThrow(new NotificationNotFoundException("notification not found"));


        mockMvc.perform(
                get("/notifications/100")
                        .with(user("testuser"))
                        .with(csrf())
        )
                .andExpect(status().isNotFound());

        verify(notificationService)
                .findNotificationById(100L);

    }


}
