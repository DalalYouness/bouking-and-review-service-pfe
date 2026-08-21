package com.dalal.boukingandreviewservicepfe.dtos.event;

import java.time.LocalDateTime;

public record ReviewCreatedEvent(
        Long reviewId,
        Long reservationId,
        Long clientId,
        Long providerId,
        Boolean isRecommended,
        String comment,
        LocalDateTime createdAt
) {}
