package com.dalal.boukingandreviewservicepfe.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // dit a jackson inclut seulement ce qui sont non null parce que le dernier, on l'utilise uniquement une fois
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
}
