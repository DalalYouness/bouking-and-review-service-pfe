package com.dalal.boukingandreviewservicepfe.services;

import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationValidateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReservationResponse;

import java.util.List;


public interface ReservationService {
    // --- Client Use Cases ---
    ReservationResponse createReservation(ReservationCreateRequest  reservationCreateRequest);
    ReservationResponse cancelReservation(Long reservationId);
    List<ReservationResponse> getClientReservations(Long clientId);

    // --- Provider Use Cases ---
    List<ReservationResponse> getProviderReservations(Long providerId);
    ReservationResponse rejectReservation(Long reservationId);
    ReservationResponse validateReservation(Long reservationId, ReservationValidateRequest request);
    ReservationResponse completeReservation(Long reservationId);
}
