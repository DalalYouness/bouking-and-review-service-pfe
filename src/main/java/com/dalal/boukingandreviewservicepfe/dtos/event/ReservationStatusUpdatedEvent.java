package com.dalal.boukingandreviewservicepfe.dtos.event;

import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;

import java.time.LocalDateTime;

public record ReservationStatusUpdatedEvent(
        Long bookingId,
        Long clientId,
        Long providerId,
        BookingStatus status,
        LocalDateTime createdAt
) {}
