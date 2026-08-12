package com.dalal.boukingandreviewservicepfe.dtos.response;

public record ProviderDashboardSatisfactionResponse(
        Long providerId,
        Long totalVotes,
        Long positiveVotesCount,
        Long negativeVotesCount,
        Double satisfactionRate,
        Double averageRating
) {}
