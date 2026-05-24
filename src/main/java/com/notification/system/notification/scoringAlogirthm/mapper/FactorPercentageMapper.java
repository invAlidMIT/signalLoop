package com.notification.system.notification.scoringAlogirthm.mapper;

import com.notification.system.notification.scoringAlogirthm.dto.FactorPercentageResponseDTO;
import com.notification.system.notification.scoringAlogirthm.entity.FactorPercentage;
import org.springframework.stereotype.Component;

@Component
public class FactorPercentageMapper {
    public FactorPercentageResponseDTO toResponse(FactorPercentage factorPercentage){
        return new FactorPercentageResponseDTO(factorPercentage.getFactor(),
                factorPercentage.getPercentage());
    }
}
