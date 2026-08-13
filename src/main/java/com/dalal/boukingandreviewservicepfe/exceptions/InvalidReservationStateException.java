package com.dalal.boukingandreviewservicepfe.exceptions;

import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;

public class InvalidReservationStateException extends RuntimeException {

    public InvalidReservationStateException(String message) {
        super(message);
    }

    public static InvalidReservationStateException forStatusTransition(BookingStatus current, BookingStatus target) {
        return new InvalidReservationStateException(
                String.format("Transition de statut impossible: impossible de passer de %s à %s.", current, target)
        );
    }

    public static InvalidReservationStateException forReviewCreation(BookingStatus current) {
        return new InvalidReservationStateException(
                String.format("Impossible d'ajouter un avis. La réservation doit être COMPLETED (Statut actuel: %s).", current)
        );
    }
}