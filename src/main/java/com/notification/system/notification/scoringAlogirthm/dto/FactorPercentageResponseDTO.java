package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FactorPercentageResponseDTO {
    private Factor factor;
    private double percentage;
}
