package com.dalal.boukingandreviewservicepfe.dtos.response;

import lombok.Builder;

@Builder
public record ProviderStatsResponse(
        Long providerId,
        // TODO (Future Use): Average rating out of 5 stars (e.g., 4.5/5.0).
        // Currently set to null or calculated from future rating field.
        // Double noteGlobale,          // Ex: 4.5
        Double tauxRecommandation,   // Ex: 85.0 (en %)
        Long totalClientsVotants     // Ex: 42
) {}