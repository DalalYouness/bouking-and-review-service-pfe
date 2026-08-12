package com.dalal.boukingandreviewservicepfe.dtos.request;



import jakarta.validation.constraints.NotNull;

public record ReviewCreateRequest(
        @NotNull(message = "L'ID de la réservation est obligatoire")
        Long reservationId,

        @NotNull(message = "Le choix de recommandation est obligatoire")
        Boolean isRecommended, // true = Recommande, false = Ne recommande pas
        String comment // Optional
) {}
