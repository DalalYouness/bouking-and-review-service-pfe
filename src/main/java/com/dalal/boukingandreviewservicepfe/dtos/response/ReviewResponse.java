package com.dalal.boukingandreviewservicepfe.dtos.response;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long idClient,
        String clientName,
        Integer rating,         // between 1 and 5
        String comment,
        boolean isRecommended,  // true and rate >= 4
        LocalDateTime createdAt // pub date
) {}
