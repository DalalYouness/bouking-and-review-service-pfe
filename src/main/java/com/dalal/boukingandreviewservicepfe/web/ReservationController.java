package com.dalal.boukingandreviewservicepfe.web;

import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.request.ReservationValidateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReservationResponse;
import com.dalal.boukingandreviewservicepfe.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /* ==========================================
       Client Endpoints (Espace Client)
       ========================================== */

    // 1. Client : Créer une nouvelle réservation
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationCreateRequest request) {
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. Client : Annuler sa propre réservation (PENDING -> CANCELLED)
    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(
            @PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    // 3. Client : Consulter l'historique de ses réservations (Paginated)
    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<ReservationResponse>> getClientReservations(
            @PathVariable Long clientId,
            @PageableDefault(size = 10, sort = "dateDemande") Pageable pageable) {
        return ResponseEntity.ok(reservationService.getClientReservations(clientId, pageable));
    }

    /* ==========================================
       Provider Endpoints (Espace Prestataire)
       ========================================== */

    // 4. Provider : Consulter les demandes de réservation reçues (Paginated)
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<Page<ReservationResponse>> getProviderReservations(
            @PathVariable Long providerId,
            @PageableDefault(size = 10, sort = "dateDemande") Pageable pageable) {
        return ResponseEntity.ok(reservationService.getProviderReservations(providerId, pageable));
    }

    // 5. Provider : Valider/Confirmer une réservation (PENDING -> CONFIRMED)
    @PatchMapping("/{reservationId}/validate")
    public ResponseEntity<ReservationResponse> validateReservation(
            @PathVariable Long reservationId,
            @Valid @RequestBody ReservationValidateRequest request) {
        return ResponseEntity.ok(reservationService.validateReservation(reservationId, request));
    }

    // 6. Provider : Refuser une demande de réservation (PENDING -> REJECTED)
    @PatchMapping("/{reservationId}/reject")
    public ResponseEntity<ReservationResponse> rejectReservation(
            @PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.rejectReservation(reservationId));
    }

    // 7. Provider : Marquer la prestation comme terminée (CONFIRMED -> COMPLETED)
    @PatchMapping("/{reservationId}/complete")
    public ResponseEntity<ReservationResponse> completeReservation(
            @PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.completeReservation(reservationId));
    }
}
