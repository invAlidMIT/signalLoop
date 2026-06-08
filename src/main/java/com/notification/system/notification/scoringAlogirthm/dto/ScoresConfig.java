package com.notification.system.notification.scoringAlogirthm.dto;

import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import com.notification.system.user.enums.Channel;
import lombok.*;

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
