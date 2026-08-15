package com.dalal.boukingandreviewservicepfe.dtos.response;

import java.time.LocalDateTime;

public record ClientReviewHistoryResponse(
        Long id,
        Long providerId,
        String providerName,
        String serviceName,
        String comment,
        boolean isRecommended,
        LocalDateTime createdAt
) {}