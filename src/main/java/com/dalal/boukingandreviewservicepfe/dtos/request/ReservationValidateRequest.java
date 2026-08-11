package com.dalal.boukingandreviewservicepfe.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservationValidateRequest(
        @NotNull(message = "La durée réelle est obligatoire")
        @Positive(message = "La durée doit être positive")
        Integer dureeReel
) {}
