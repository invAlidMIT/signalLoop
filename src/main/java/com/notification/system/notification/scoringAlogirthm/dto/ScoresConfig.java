package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import com.notification.system.user.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ScoresConfig {

    private Map<Factor,Double> factorWeights;
    private Map<Channel,Double> reliabilityScores;
    private Map<String,Double> UrgencyWeights;

}
