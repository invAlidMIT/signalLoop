package com.notification.system.notification.scoringAlogirthm.service;

import com.notification.system.notification.scoringAlogirthm.dto.FactorWeightsRequestDTO;
import com.notification.system.notification.scoringAlogirthm.entity.FactorPercentage;
import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import com.notification.system.notification.scoringAlogirthm.repository.FactorPercentageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactorPercentageService {

    private final FactorPercentageRepository factorPercentageRepository;

    @CacheEvict(value = "scores-config",allEntries = true)
    public void updateFactors(FactorWeightsRequestDTO factorWeightsRequestDTO){
        for (Factor f:factorWeightsRequestDTO.getFactorWeights().keySet()){
            FactorPercentage factorPercentage=
                    factorPercentageRepository.findByFactor(f)
                            .orElseThrow(()->new RuntimeException("Factor not found"));
            factorPercentage.setPercentage(factorWeightsRequestDTO.getFactorWeights().get(f));
            factorPercentageRepository.save(factorPercentage);
        }
    }
}
