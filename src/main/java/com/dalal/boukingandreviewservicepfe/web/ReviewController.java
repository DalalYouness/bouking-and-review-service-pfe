package com.dalal.boukingandreviewservicepfe.web;

import com.dalal.boukingandreviewservicepfe.dtos.request.ReviewCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ClientReviewHistoryResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ProviderDashboardSatisfactionResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ProviderStatsResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReviewResponse;
import com.dalal.boukingandreviewservicepfe.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /* ==========================================
       Client Endpoints (Espace Client)
       ========================================== */

    // 1. Client : Ajouter un avis / voter pour une prestation terminée
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @Valid @RequestBody ReviewCreateRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. Client : Consulter ses propres avis (Historique paginé par date de publication)
    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<ClientReviewHistoryResponse>> getClientReviewHistory(
            @PathVariable Long clientId,
            @PageableDefault(size = 10, sort = "datePublication", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reviewService.getClientReviewHistory(clientId, pageable));
    }

    /* ==========================================
       Public / All Users Endpoints
       ========================================== */

    // 3. All Users : Consulter la liste des avis d'un prestataire (Paginé)
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<Page<ReviewResponse>> getProviderReviews(
            @PathVariable Long providerId,
            @PageableDefault(size = 10, sort = "datePublication", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reviewService.getProviderReviews(providerId, pageable));
    }

    // 4. All Users : Consulter les statistiques / Note global d'un prestataire
    @GetMapping("/provider/{providerId}/stats")
    public ResponseEntity<ProviderStatsResponse> getProviderStats(
            @PathVariable Long providerId) {
        return ResponseEntity.ok(reviewService.getProviderStats(providerId));
    }

    /* ==========================================
       Provider Endpoints (Espace Prestataire)
       ========================================== */

    // 5. Provider : Consulter le tableau de bord de satisfaction
    @GetMapping("/provider/{providerId}/dashboard")
    public ResponseEntity<ProviderDashboardSatisfactionResponse> getProviderSatisfactionDashboard(
            @PathVariable Long providerId) {
        return ResponseEntity.ok(reviewService.getProviderSatisfactionDashboard(providerId));
    }
}