package com.notification.system.notification.scoringAlogirthm.service;

import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import com.notification.system.notification.scoringAlogirthm.entity.FactorPercentage;
import com.notification.system.notification.scoringAlogirthm.entity.UrgencyWeight;
import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import com.notification.system.notification.scoringAlogirthm.repository.FactorPercentageRepository;
import com.notification.system.notification.scoringAlogirthm.repository.UrgencyWeightRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ScoringConfigService {

    private static final Logger log = LoggerFactory.getLogger(ScoringConfigService.class);
    private final FactorPercentageRepository factorPercentageRepository;
    private final UrgencyWeightRepository urgencyWeightRepository;

    @Cacheable("scores-config")
    public ScoresConfig loadScores(){
        log.info("Loading scoring config from DB");
               Map<Factor,Double> factorWeights = getFactorWeights();
               Map<String,Double> urgencyWeights = getUrgencyWeights();
               return new ScoresConfig(factorWeights,urgencyWeights);
    }

    public Map<Factor,Double> getFactorWeights(){
        return factorPercentageRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        FactorPercentage::getFactor,
                        FactorPercentage::getPercentage
                ));
    }

    public Map<String,Double> getUrgencyWeights(){
        return urgencyWeightRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        urgencyWeight -> urgencyWeight.getUrgency().name() + "_" + urgencyWeight.getChannel().name(),
                        UrgencyWeight::getUrgencyPercentage
                ));
    }
}
