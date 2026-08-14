package com.dalal.boukingandreviewservicepfe.services;

import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationValidateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReservationResponse;
import com.dalal.boukingandreviewservicepfe.entities.Reservation;
import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;
import com.dalal.boukingandreviewservicepfe.exceptions.InvalidReservationStateException;
import com.dalal.boukingandreviewservicepfe.exceptions.ResourceNotFoundException;
import com.dalal.boukingandreviewservicepfe.mappers.ReservationMapper;
import com.dalal.boukingandreviewservicepfe.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    // --- Local Dependencies ---
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    // --- Synchronous REST Clients (Validation - Deferred) ---
//  private final UserClient userClient;
//  private final ProviderClient providerClient;

    // --- Asynchronous Event Publisher (Kafka - Deferred) ---
//  private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Centralized Helper Method for Encapsulating State Transitions and Persistence (DRY Principle)
     */
    private Reservation applyStatusTransition(Long reservationId, BookingStatus expectedSourceStatus, BookingStatus targetStatus) {
        // 1. Guard Clause: Protection against null ID
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation id cannot be null");
        }

        // 2. Fetch Entity and Business Check: Ensure reservation exists
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> ResourceNotFoundException.forReservation(reservationId));

        // 3. Domain Rule / State Validation: Ensure current status matches expected source state
        if (reservation.getStatus() != expectedSourceStatus) {
            throw InvalidReservationStateException.forStatusTransition(
                    reservation.getStatus(),
                    expectedSourceStatus
            );
        }

        // 4. State Transition & Persistence
        reservation.setStatus(targetStatus);
        return reservationRepository.save(reservation);
    }

    /* ================
       Client Use Cases
       ================*/

    @Override
    public ReservationResponse createReservation(ReservationCreateRequest reservationCreateRequest) {
        // 1. Guard Clause: Request payload check
        if (reservationCreateRequest == null) {
            throw new IllegalArgumentException("Reservation request cannot be null");
        }

        // 2. Business Validation (External Checks)
        // TODO: Verify client exists via userClient
        // TODO: Verify provider and service exist via providerClient
        // TODO: Check double-booking / availability in Database

        // 3. Mapping: Request DTO -> Entity
        Reservation reservation = reservationMapper.toEntity(reservationCreateRequest);

        // 4. Persistence: Save new entity (Default status is PENDING)
        Reservation savedReservation = reservationRepository.save(reservation);

        // 5. Asynchronous Events / Notifications
        // TODO: Publish ReservationCreatedEvent via Kafka template

        // 6. Data Transfer Mapping: Return Response DTO
        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    public ReservationResponse cancelReservation(Long reservationId) {
        // 1. Apply State Transition: PENDING -> CANCELLED
        Reservation cancelledReservation = applyStatusTransition(reservationId, BookingStatus.PENDING, BookingStatus.CANCELLED);

        // 2. Asynchronous Integration Event (Deferred Task)
        // TODO: Publish ReservationCancelledEvent via Kafka template

        // 3. Return Response DTO
        return reservationMapper.toResponse(cancelledReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationResponse> getClientReservations(Long clientId, Pageable pageable) {
        // 1. Guard Clause: Protection against null inputs
        if (clientId == null) {
            throw new IllegalArgumentException("Client id cannot be null");
        }

        // 2. Fetch Page of Entities from Repository
        Page<Reservation> reservationPage = reservationRepository.findByIdClient(clientId, pageable);

        // 3. Transform Page<Reservation> -> Page<ReservationResponse>
        return reservationPage.map(reservationMapper::toResponse);
    }

    /* =====================
       Provider Use Cases
       ===================== */

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationResponse> getProviderReservations(Long providerId, Pageable pageable) {
        // 1. Guard Clause: Protection against null inputs
        if (providerId == null) {
            throw new IllegalArgumentException("Provider id cannot be null");
        }

        // 2. Fetch Page of Entities from Repository
        Page<Reservation> reservationPage = reservationRepository.findByIdProvider(providerId, pageable);

        // 3. Transform Page<Reservation> -> Page<ReservationResponse>
        return reservationPage.map(reservationMapper::toResponse);
    }

    @Override
    public ReservationResponse rejectReservation(Long reservationId) {
        // 1. Apply State Transition: PENDING -> REJECTED
        Reservation rejectedReservation = applyStatusTransition(reservationId, BookingStatus.PENDING, BookingStatus.REJECTED);

        // 2. Asynchronous Integration Event "Deferred Task"
        // TODO: Publish ReservationRejectedEvent via Kafka template

        // 3. Return Response DTO
        return reservationMapper.toResponse(rejectedReservation);
    }

    @Override
    public ReservationResponse validateReservation(Long reservationId, ReservationValidateRequest request) {
        // 1. Guard Clause: Request payload check
        if (request == null) {
            throw new IllegalArgumentException("Validation request payload cannot be null");
        }

        // 2. Apply State Transition: PENDING -> CONFIRMED
        Reservation confirmedReservation = applyStatusTransition(reservationId, BookingStatus.PENDING, BookingStatus.CONFIRMED);

        // 3. Asynchronous Integration Event (Deferred Task)
        // TODO: Publish ReservationConfirmedEvent via Kafka template

        // 4. Return Response DTO
        return reservationMapper.toResponse(confirmedReservation);
    }

    @Override
    public ReservationResponse completeReservation(Long reservationId) {
        // 1. Apply State Transition: CONFIRMED -> COMPLETED
        Reservation completedReservation = applyStatusTransition(reservationId, BookingStatus.CONFIRMED, BookingStatus.COMPLETED);

        // 2. Asynchronous Integration Event (Deferred Task)
        // TODO: Publish ReservationCompletedEvent via Kafka template

        // 3. Return Response DTO
        return reservationMapper.toResponse(completedReservation);
    }
}