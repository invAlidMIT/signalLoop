package com.notification.system.notification.reliabilityMetrics.repository;

import com.notification.system.notification.reliabilityMetrics.entity.ChannelMetrics;
import com.notification.system.user.enums.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelMetricsRepository extends JpaRepository<ChannelMetrics, Channel> {
}
