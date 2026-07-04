package com.notification.system.notification.mapper;

import com.notification.system.notification.dto.NotificationRequestDTO;
import com.notification.system.notification.dto.NotificationResponseDTO;
import com.notification.system.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NotificationMapper {

    @Mapping(target = "notificationId",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    public Notification toEntity(NotificationRequestDTO requestDTO);

    @Mapping(source="user.id",target = "userId")
    public NotificationResponseDTO toResponse(Notification notification);
}
