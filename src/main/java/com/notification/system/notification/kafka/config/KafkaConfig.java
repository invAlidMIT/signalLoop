package com.notification.system.notification.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic createNotificationEventsTopic(){
        return TopicBuilder.name("notification-events")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
