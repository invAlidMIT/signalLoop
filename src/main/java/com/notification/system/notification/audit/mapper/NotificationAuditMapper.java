package com.notification.system.notification.audit.mapper;

import com.notification.system.notification.audit.dto.NotificationAuditDTO;
import com.notification.system.notification.audit.entity.NotificationAudit;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy=NullValuePropertyMappingStrategy.IGNORE
)
public interface NotificationAuditMapper {

    public NotificationAuditDTO toResponseDTO(
            NotificationAudit notificationAudit);

    public NotificationAudit toEntity(
            NotificationAuditDTO notificationAuditDTO
    );

}
