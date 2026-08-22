package com.dalal.boukingandreviewservicepfe.messaging;

import com.dalal.boukingandreviewservicepfe.config.KafkaTopicConfig;
import com.dalal.boukingandreviewservicepfe.dtos.event.ReservationCreatedEvent;
import com.dalal.boukingandreviewservicepfe.dtos.event.ReservationStatusUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendReservationCreatedEvent(ReservationCreatedEvent event) {
        log.info("Publishing ReservationCreatedEvent to topic '{}': {}", KafkaTopicConfig.RESERVATION_EVENTS_TOPIC, event);
        kafkaTemplate.send(KafkaTopicConfig.RESERVATION_EVENTS_TOPIC, event.bookingId().toString(), event);
    }
    public void sendReservationStatusUpdatedEvent(ReservationStatusUpdatedEvent event) {
        log.info("Publishing ReservationStatusUpdatedEvent to topic '{}': {}", KafkaTopicConfig.RESERVATION_EVENTS_TOPIC, event);
        kafkaTemplate.send(KafkaTopicConfig.RESERVATION_EVENTS_TOPIC, event.bookingId().toString(), event);
    }
}
