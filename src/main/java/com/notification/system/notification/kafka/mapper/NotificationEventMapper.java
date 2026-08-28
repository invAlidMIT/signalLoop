package com.notification.system.notification.kafka.mapper;

import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.kafka.dto.NotificationEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NotificationEventMapper {

    public NotificationEventDTO toNotificationEventDTO(Notification notification);
}
