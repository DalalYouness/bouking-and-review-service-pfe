package com.dalal.boukingandreviewservicepfe.dtos.response;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        // Long idClient, for future use , because it would be useful if we want display client profil
        // Long reservationId
        String clientName,
        String comment,
        boolean isRecommended,
        LocalDateTime createdAt // pub date
) {}
