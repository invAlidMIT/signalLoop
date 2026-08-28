package com.notification.system.notification.scoringAlogirthm.dto;


import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FactorWeightsRequestDTO {

    Map<Factor,Double> factorWeights;

}
