package com.notification.system.notification.reliabilityMetrics.mapper;

import com.notification.system.notification.reliabilityMetrics.dto.ChannelMetricsDTO;
import com.notification.system.notification.reliabilityMetrics.entity.ChannelMetrics;
import org.springframework.stereotype.Component;

@Component
public class ChannelMetricsMapper {

    public ChannelMetricsDTO toResponse(ChannelMetrics channelMetrics){
        ChannelMetricsDTO channelMetricsDTO=new ChannelMetricsDTO();
        channelMetricsDTO.setChannel(channelMetrics.getChannel());
        channelMetricsDTO.setSuccessCount(channelMetrics.getSuccessCount());
        channelMetricsDTO.setFailureCount(channelMetrics.getFailureCount());
        channelMetricsDTO.setRetryCount(channelMetrics.getRetryCount());
        channelMetricsDTO.setLastUpdatedAt(channelMetrics.getLastUpdatedAt());
        return channelMetricsDTO;
    }

    public ChannelMetrics toEntity(ChannelMetricsDTO channelMetricsDTO){
        ChannelMetrics channelMetrics=new ChannelMetrics();
        channelMetrics.setChannel(channelMetricsDTO.getChannel());
        channelMetrics.setSuccessCount(channelMetricsDTO.getSuccessCount());
        channelMetrics.setFailureCount(channelMetricsDTO.getFailureCount());
        channelMetrics.setRetryCount(channelMetricsDTO.getRetryCount());
        channelMetrics.setLastUpdatedAt(channelMetricsDTO.getLastUpdatedAt());
        return channelMetrics;
    }
}
