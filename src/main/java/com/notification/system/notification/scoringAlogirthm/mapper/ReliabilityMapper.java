package com.notification.system.notification.scoringAlogirthm.mapper;

import com.notification.system.notification.scoringAlogirthm.dto.ReliabilityResponseDTO;
import com.notification.system.notification.scoringAlogirthm.entity.Reliability;
import org.springframework.stereotype.Component;

@Component
public class ReliabilityMapper {
    public ReliabilityResponseDTO toResponse(Reliability reliability){
       return new ReliabilityResponseDTO(reliability.getChannel(),
                reliability.getValue());
    }
}
