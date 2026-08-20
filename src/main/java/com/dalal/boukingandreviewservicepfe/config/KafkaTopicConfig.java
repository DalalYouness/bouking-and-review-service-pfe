package com.dalal.boukingandreviewservicepfe.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic reviewEventsTopic() {
        return TopicBuilder.name("review-events-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic reservationEventsTopic() {
        return TopicBuilder.name("reservation-events-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}