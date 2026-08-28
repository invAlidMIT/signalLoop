package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoresConfig implements Serializable {

    private Map<Factor,Double> factorWeights;
    private Map<String,Double> UrgencyWeights;

}
