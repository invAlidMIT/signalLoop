package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.notification.scoringAlogirthm.enums.Urgency;
import com.notification.system.user.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UrgencyWeightResponseDTO {

    private Urgency urgency;
    private Channel channel;
    private double percentage;
}
