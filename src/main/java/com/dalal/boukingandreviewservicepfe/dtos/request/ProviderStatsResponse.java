package com.dalal.boukingandreviewservicepfe.dtos.request;

public record ProviderStatsResponse(
        Long providerId,
        Double noteGlobale,          // Ex: 4.5
        Double tauxRecommandation,   // Ex: 85.0 (en %)
        Long totalClientsVotants     // Ex: 42
) {}