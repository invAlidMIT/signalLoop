package com.notification.system.notification.scoringAlogirthm.service;

import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import com.notification.system.notification.scoringAlogirthm.entity.FactorPercentage;
import com.notification.system.notification.scoringAlogirthm.entity.UrgencyWeight;
import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import com.notification.system.notification.scoringAlogirthm.redis.ScoresCacheConfig;
import com.notification.system.notification.scoringAlogirthm.repository.FactorPercentageRepository;
import com.notification.system.notification.scoringAlogirthm.repository.UrgencyWeightRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final ScoresCacheConfig scoresCacheConfig;

    public ScoresConfig loadScores(){

        ScoresConfig scoresConfig=scoresCacheConfig.getScoresConfig();
        if(scoresConfig!=null){
            log.info("ScoresConfig cache hit");
            return scoresConfig;
        }
               Map<Factor,Double> factorWeights = factorPercentageRepository.findAll()
                        .stream()
                        .collect(Collectors.toMap(
                                FactorPercentage::getFactor,
                                FactorPercentage::getPercentage
                        ));

               Map<String,Double> urgencyWeights = urgencyWeightRepository.findAll()
                        .stream()
                        .collect(Collectors.toMap(
                                urgencyWeight -> urgencyWeight.getUrgency().name() + "_" + urgencyWeight.getChannel().name(),
                                UrgencyWeight::getUrgencyPercentage
                        ));
               scoresConfig=new ScoresConfig(factorWeights,
                       urgencyWeights);
               scoresCacheConfig.setScoresConfig(scoresConfig);
               return scoresConfig;
    }
}
