package com.dalal.boukingandreviewservicepfe.exceptions;

public class ReviewAlreadyExistsException extends RuntimeException {

    public ReviewAlreadyExistsException(String message) {
        super(message);
    }

    public static ReviewAlreadyExistsException forReservation(Long reservationId) {
        return new ReviewAlreadyExistsException(
                String.format("Un avis existe déjà pour la réservation ID %d.", reservationId)
        );
    }
}
