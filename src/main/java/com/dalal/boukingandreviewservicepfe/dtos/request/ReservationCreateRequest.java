package com.dalal.boukingandreviewservicepfe.dtos.request;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record ReservationCreateRequest(
        @NotNull(message = "Le client est obligatoire")
        Long idClient,

        @NotNull(message = "Le prestataire est obligatoire")
        Long idProvider,

        @NotNull(message = "Le service est obligatoire")
        Long idService,

        @NotNull(message = "La date du rendez-vous est obligatoire")
        @Future(message = "La date du rendez-vous doit être dans le futur")
        LocalDateTime dateRdv,

        // it's not required for now
        Integer dureeReel
) {}
