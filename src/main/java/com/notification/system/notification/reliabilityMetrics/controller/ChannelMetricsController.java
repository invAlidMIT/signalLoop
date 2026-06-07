package com.notification.system.notification.reliabilityMetrics.controller;

import com.notification.system.notification.reliabilityMetrics.dto.ChannelMetricsDTO;
import com.notification.system.notification.reliabilityMetrics.mapper.ChannelMetricsMapper;
import com.notification.system.notification.reliabilityMetrics.service.ChannelMetricsService;
import com.notification.system.user.enums.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics/channels")
@RequiredArgsConstructor
public class ChannelMetricsController {

    private final ChannelMetricsService channelMetricsService;
    private final ChannelMetricsMapper channelMetricsMapper;

    @GetMapping("/{channelName}")
    public ChannelMetricsDTO getChannelMetricsByChannel(@PathVariable("channelName") Channel channel){
        return channelMetricsMapper.toResponse(channelMetricsService.getChannelMetricsByChannel(channel));
    }
}
