package com.notification.system.notification.reliabilityMetrics.calculate;

import com.notification.system.user.enums.Channel;

public interface ReliabilityCalculator {
    public double score(Channel channel);
}
