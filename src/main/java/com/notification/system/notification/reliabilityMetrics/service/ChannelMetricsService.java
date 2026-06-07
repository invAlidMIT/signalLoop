package com.notification.system.notification.reliabilityMetrics.service;

import com.notification.system.notification.reliabilityMetrics.entity.ChannelMetrics;
import com.notification.system.notification.reliabilityMetrics.mapper.ChannelMetricsMapper;
import com.notification.system.notification.reliabilityMetrics.repository.ChannelMetricsRepository;
import com.notification.system.user.enums.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ChannelMetricsService {

    private final ChannelMetricsRepository channelMetricsRepository;

    public ChannelMetrics getChannelMetricsByChannel(Channel channel){
        return channelMetricsRepository.findById(channel)
                .orElseThrow(()->new RuntimeException("Channel Metrics not found"));
    }

    public void recordSuccess(Channel channel){
        updateMetrics(channel,
                channelMetrics ->
                channelMetrics.setSuccessCount(channelMetrics.getSuccessCount()+1));
    }

    public void recordFailure(Channel channel){
        updateMetrics(channel,
                channelMetrics ->
                        channelMetrics.setFailureCount(channelMetrics.getFailureCount()+1));
    }
    public void recordRetry(Channel channel){
        updateMetrics(channel,
                channelMetrics ->
                        channelMetrics.setRetryCount(channelMetrics.getRetryCount()+1));
    }

    public void updateMetrics(Channel channel,
                              Consumer<ChannelMetrics> updater){
        ChannelMetrics channelMetrics=getChannelMetricsByChannel(channel);
        updater.accept(channelMetrics);
        channelMetricsRepository.save(channelMetrics);
    }



}
