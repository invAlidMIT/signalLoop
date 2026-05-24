package com.notification.system.notification.scoringAlogirthm.repository;

import com.notification.system.notification.scoringAlogirthm.entity.FactorPercentage;
import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactorPercentageRepository extends JpaRepository<FactorPercentage,Long> {
}
