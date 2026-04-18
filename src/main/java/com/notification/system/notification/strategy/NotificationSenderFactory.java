package com.notification.system.notification.strategy;

import com.notification.system.user.enums.Channel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationSenderFactory {
    private final Map<Channel,NotificationSender> senderMap;

    public NotificationSenderFactory(List<NotificationSender> senders){
        senderMap=Map.of(
                Channel.EMAIL,senders.stream()
                        .filter(s -> s instanceof EmailSender)
                        .findFirst().orElseThrow(),

                Channel.SMS,senders.stream()
                        .filter(s-> s instanceof SmsSender)
                        .findFirst().orElseThrow(),

                Channel.PUSH,senders.stream()
                        .filter(s-> s instanceof PushSender)
                        .findFirst().orElseThrow()
        );
    }

    public NotificationSender getSender(Channel channel){
        return senderMap.get(channel);
    }
}
