package com.dalal.boukingandreviewservicepfe.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class KafkaTopicConfig {

    public static final String RESERVATION_EVENTS_TOPIC = "reservation-events-topic";
    public static final String REVIEW_EVENTS_TOPIC = "review-events-topic";

    @Bean
    public NewTopic reservationEventsTopic() {
        return TopicBuilder.name(RESERVATION_EVENTS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic reviewCreatedTopic() {
        return TopicBuilder.name(REVIEW_EVENTS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}