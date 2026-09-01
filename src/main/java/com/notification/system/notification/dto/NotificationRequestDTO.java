package com.notification.system.notification.dto;


import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDTO {

    @NotNull
    private Long userId;
    @NotBlank
    private String message;

    @Enumerated(EnumType.STRING)
    private Urgency urgency;
}
