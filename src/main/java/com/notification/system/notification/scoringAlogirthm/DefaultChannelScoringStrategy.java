package com.notification.system.notification.scoringAlogirthm;

import com.notification.system.notification.entity.Notification;
import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import com.notification.system.notification.scoringAlogirthm.enums.Factor;
import com.notification.system.user.entity.User;
import com.notification.system.user.enums.Channel;
import org.springframework.stereotype.Service;

@Service
public class DefaultChannelScoringStrategy implements ChannelScoringStrategy{

    @Override
    public double score(User user, Notification notification, Channel channel,ScoresConfig scoresConfig) {

        Double preferenceWeight=scoresConfig.getFactorWeights().get(Factor.USER_PREFERENCE);
        Double reliabilityWeight=scoresConfig.getFactorWeights().get(Factor.RELIABILITY);
        Double urgencyWeight=scoresConfig.getFactorWeights().get(Factor.URGENCY);

        double preferredChannelScore=user.getPreferredChannel()==channel ? 100:0;
        double reliabilityScore=scoresConfig.getReliabilityScores().get(channel);
        String urgencyKey=notification.getUrgency().name()+"_"+channel.name();
        double urgencyScore=scoresConfig.getUrgencyWeights().get(urgencyKey);

        return (preferenceWeight * preferredChannelScore)/100
                +
                (reliabilityWeight * reliabilityScore)/100
                +
                (urgencyWeight*urgencyScore)/100;

    }
}
