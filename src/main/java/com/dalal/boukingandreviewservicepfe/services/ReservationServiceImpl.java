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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {
    // --- Local Dependencies ---
    private final ReservationRepository reservationRepository;
    private final ReservationMapper  reservationMapper;

    // --- Synchronous REST Clients (Validation) ---
//    private final UserClient userClient;
//    private final ProviderClient providerClient;

    // --- Asynchronous Event Publisher (Kafka) ---
//    private final KafkaTemplate<String, ReservationCreatedEvent> kafkaTemplate;

    @Override
    public ReservationResponse createReservation(ReservationCreateRequest reservationCreateRequest) {
        // 1. validation (guard clause)
        if(reservationCreateRequest == null){
            throw new IllegalArgumentException("Reservation request cannot be null");
        }
        // business logique
        // 2. Business Validation (External Checks)
        // TODO: Verify client exists via userClient
        // TODO: Verify provider and service exist via providerClient
        // TODO: Check double-booking / availability f Database

        // 3. mapping
        Reservation reservation = reservationMapper.toEntity(reservationCreateRequest);
        // reservation.setStatus(BookingStatus.PENDING); it's not important because we have already used as default status value

        // 4. save
        Reservation savedReservation = reservationRepository.save(reservation);

        // 5. Async Events / Notifications
        // TODO: Publish ReservationCreatedEvent via Kafka

        // 6. return response
        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    public ReservationResponse cancelReservation(Long reservationId) {
        // 1. Guard Clause: Protection against null ID
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation id cannot be null");
        }

        // 2. Fetch Entity and Business Check: Ensure the reservation exists
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> ResourceNotFoundException.forReservation(reservationId));

        // 3. Domain Rule / State Validation: Only PENDING reservations can be cancelled
        if (reservation.getStatus() != BookingStatus.PENDING) {
            throw InvalidReservationStateException.forStatusTransition(
                    reservation.getStatus(),
                    BookingStatus.PENDING
            );
        }
        // 4. State Transition: Apply cancellation
        reservation.setStatus(BookingStatus.CANCELLED);

        // 5. Persistence: Save updated state
        Reservation savedReservation = reservationRepository.save(reservation);

        // 6. Asynchronous Integration Event (Deferred Task)
        // TODO: Publish ReservationCancelledEvent via Kafka template

        // 7. Data Transfer Mapping: Return Response DTO
        return reservationMapper.toResponse(savedReservation);
    }

    @Override
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

    @Override
    public List<ReservationResponse> getProviderReservations(Long providerId) {
        return List.of();
    }

    @Override
    public ReservationResponse rejectReservation(Long reservationId) {
        return null;
    }

    @Override
    public ReservationResponse validateReservation(Long reservationId, ReservationValidateRequest request) {
        return null;
    }

    @Override
    public ReservationResponse completeReservation(Long reservationId) {
        return null;
    }
}
