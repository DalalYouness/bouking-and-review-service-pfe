package com.dalal.boukingandreviewservicepfe.dtos.response;

import lombok.Builder;

@Builder
public record ProviderDashboardSatisfactionResponse(
        Long providerId,
        Long totalVotes,
        Long positiveVotesCount,
        Long negativeVotesCount,
        Double tauxRecommendation
        // TODO: future use
        // Double satisfactionRate,
        // Double averageRating
) {}
