package com.notification.system.notification.reliabilityMetrics.calculate;

import com.notification.system.notification.reliabilityMetrics.entity.ChannelMetrics;
import com.notification.system.notification.reliabilityMetrics.service.ChannelMetricsService;
import com.notification.system.user.enums.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DynamicReliabilityCalculator implements ReliabilityCalculator {

    private final ChannelMetricsService channelMetricsService;

    @Override
    public double score(Channel channel) {
        ChannelMetrics channelMetrics= channelMetricsService.getChannelMetricsByChannel(channel);
        long total=channelMetrics.getSuccessCount()+channelMetrics.getFailureCount();
        if(total==0) return 50.0;
        return (double) (channelMetrics.getSuccessCount()/total)*100;
    }
}
