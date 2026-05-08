package com.notification.system.notification.strategy;

import com.notification.system.user.enums.Channel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationSenderFactory {
    private final Map<Channel,NotificationSender> senderMap;

    public NotificationSenderFactory(List<NotificationSender> senders){
        senderMap=senders.stream()
                .collect(Collectors.toMap(NotificationSender::getChannel,s->s));
    }

    public NotificationSender getSender(Channel channel){
        return senderMap.get(channel);
    }
}
