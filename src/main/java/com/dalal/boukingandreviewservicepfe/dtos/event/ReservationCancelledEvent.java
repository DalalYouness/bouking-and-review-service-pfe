package com.dalal.boukingandreviewservicepfe.dtos.event;

import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;

import java.time.LocalDateTime;

public record ReservationCancelledEvent(
        Long bookingId,
        Long clientId,
        Long providerId,
        BookingStatus status,
        LocalDateTime createdAt
) {}
