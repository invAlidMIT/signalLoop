package com.notification.system.notification.scoringAlogirthm.repository;

import com.notification.system.notification.scoringAlogirthm.entity.Reliability;
import com.notification.system.user.enums.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliabilityRepository extends JpaRepository<Reliability,Long> {
}
