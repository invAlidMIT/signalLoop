package com.notification.system.notification.scoringAlogirthm.mapper;

import com.notification.system.notification.scoringAlogirthm.dto.FactorPercentageResponseDTO;
import com.notification.system.notification.scoringAlogirthm.entity.FactorPercentage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FactorPercentageMapper {
    public FactorPercentageResponseDTO toResponse(FactorPercentage factorPercentage);
}
