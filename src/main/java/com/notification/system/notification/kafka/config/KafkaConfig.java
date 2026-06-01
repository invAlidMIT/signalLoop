package com.notification.system.notification.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${app.topicName}")
    private String topicName;
    @Value("${app.partitionCount}")
    private int partitionCount;
    @Value("${app.replicaCount}")
    private int replicaCount;

    @Bean
    public NewTopic createNotificationEventsTopic(){
        return TopicBuilder.name(topicName)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }
}
