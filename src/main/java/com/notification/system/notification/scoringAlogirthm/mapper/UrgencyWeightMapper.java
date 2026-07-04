package com.notification.system.notification.scoringAlogirthm.mapper;


import com.notification.system.notification.scoringAlogirthm.dto.UrgencyWeightResponseDTO;
import com.notification.system.notification.scoringAlogirthm.entity.UrgencyWeight;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UrgencyWeightMapper {

    public UrgencyWeightResponseDTO toResponse(UrgencyWeight urgencyWeight);
}
