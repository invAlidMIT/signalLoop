package com.notification.system.notification.reliabilityMetrics.dto;

import com.notification.system.user.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelMetricsDTO {
    private Channel channel;
    private long successCount;
    private long failureCount;
    private long retryCount;
    private LocalDateTime lastUpdatedAt;
}
