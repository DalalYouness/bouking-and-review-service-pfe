package com.dalal.boukingandreviewservicepfe.dtos.response;

import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        LocalDateTime dateRdv,
        Integer dureeReel,
        BookingStatus status,
        Long idClient,
        Long idProvider,
        Long idService
        // ReviewResponse review // TODO: Uncomment when integrating Review Use Cases
) {}
