package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import com.notification.system.user.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UrgencyWeightResponseDTO {

    private Urgency urgency;
    private Channel channel;
    private double percentage;
}
