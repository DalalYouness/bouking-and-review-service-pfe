package com.dalal.boukingandreviewservicepfe.services;

import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationValidateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ReservationService {
    // --- Client Use Cases ---
    ReservationResponse createReservation(ReservationCreateRequest  reservationCreateRequest);
    ReservationResponse cancelReservation(Long reservationId);
    Page<ReservationResponse> getClientReservations(Long clientId, Pageable pageable);

    // --- Provider Use Cases ---
    Page<ReservationResponse> getProviderReservations(Long providerId,Pageable pageable);
    ReservationResponse rejectReservation(Long reservationId);
    ReservationResponse validateReservation(Long reservationId, ReservationValidateRequest request);
    ReservationResponse completeReservation(Long reservationId);
}
