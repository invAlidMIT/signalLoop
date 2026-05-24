package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.user.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReliabilityResponseDTO {
    private Channel channel;
    private double value;
}
