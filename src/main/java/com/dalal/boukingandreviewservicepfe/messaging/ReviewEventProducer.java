package com.dalal.boukingandreviewservicepfe.messaging;

import com.dalal.boukingandreviewservicepfe.config.KafkaTopicConfig;
import com.dalal.boukingandreviewservicepfe.dtos.event.ReservationCreatedEvent;
import com.dalal.boukingandreviewservicepfe.dtos.event.ReviewCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewEventProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void sendReviewCreatedEvent(ReviewCreatedEvent event)
    {
        log.info("Publishing ReviewCreatedEvent to topic '{}': {}", KafkaTopicConfig.REVIEW_EVENTS_TOPIC, event);
        kafkaTemplate.send(KafkaTopicConfig.REVIEW_EVENTS_TOPIC, event);
    }
}
