package com.dalal.boukingandreviewservicepfe.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forReservation(Long id) {
        return new ResourceNotFoundException(
                String.format("La réservation avec l'ID %d est introuvable.", id)
        );
    }

    public static ResourceNotFoundException forReview(Long id) {
        return new ResourceNotFoundException(
                String.format("L'avis avec l'ID %d est introuvable.", id)
        );
    }
}
