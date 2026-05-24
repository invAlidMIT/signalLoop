package com.notification.system.notification.scoringAlogirthm.mapper;


import com.notification.system.notification.scoringAlogirthm.dto.UrgencyWeightResponseDTO;
import com.notification.system.notification.scoringAlogirthm.entity.UrgencyWeight;
import org.springframework.stereotype.Component;

@Component
public class UrgencyWeightMapper {

    public UrgencyWeightResponseDTO toResponse(UrgencyWeight urgencyWeight){
        return new UrgencyWeightResponseDTO(urgencyWeight.getUrgency(),
                urgencyWeight.getChannel(),
                urgencyWeight.getUrgencyPercentage());
    }
}
