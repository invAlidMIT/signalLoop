package com.notification.system.notification.reliabilityMetrics.mapper;

import com.notification.system.notification.reliabilityMetrics.dto.ChannelMetricsDTO;
import com.notification.system.notification.reliabilityMetrics.entity.ChannelMetrics;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ChannelMetricsMapper {

    public ChannelMetricsDTO toResponse(ChannelMetrics channelMetrics);
}
