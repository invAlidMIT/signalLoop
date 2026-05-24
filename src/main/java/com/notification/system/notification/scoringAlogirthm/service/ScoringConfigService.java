package com.notification.system.notification.scoringAlogirthm.service;

import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import com.notification.system.notification.scoringAlogirthm.entity.FactorPercentage;
import com.notification.system.notification.scoringAlogirthm.entity.Reliability;
import com.notification.system.notification.scoringAlogirthm.entity.UrgencyWeight;
import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import com.notification.system.notification.scoringAlogirthm.repository.FactorPercentageRepository;
import com.notification.system.notification.scoringAlogirthm.repository.ReliabilityRepository;
import com.notification.system.notification.scoringAlogirthm.repository.UrgencyWeightRepository;
import com.notification.system.user.enums.Channel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScoringConfigService {

    private final FactorPercentageRepository factorPercentageRepository;
    private final ReliabilityRepository reliabilityRepository;
    private final UrgencyWeightRepository urgencyWeightRepository;

    public ScoresConfig loadScores(){
        Map<Factor,Double> factorWeights=factorPercentageRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        FactorPercentage::getFactor,
                        FactorPercentage::getPercentage
                ));

        Map<Channel,Double> reliabilityScores=reliabilityRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Reliability::getChannel,
                        Reliability::getValue
                ));

        Map<String,Double> urgencyWeights=urgencyWeightRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        urgencyWeight->urgencyWeight.getUrgency().name()+"_"+urgencyWeight.getChannel().name(),
                        UrgencyWeight::getUrgencyPercentage
                ));

        return new ScoresConfig(
                factorWeights,
                reliabilityScores,
                urgencyWeights
        );
    }
}
