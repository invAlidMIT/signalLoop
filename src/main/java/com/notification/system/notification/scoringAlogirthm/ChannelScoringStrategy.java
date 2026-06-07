package com.notification.system.notification.scoringAlogirthm;

import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.reliabilityMetrics.entity.ChannelMetrics;
import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import com.notification.system.user.entity.User;
import com.notification.system.user.enums.Channel;

public interface ChannelScoringStrategy {
    double score(User user, Notification notification, Channel channel, ScoresConfig config);
}
