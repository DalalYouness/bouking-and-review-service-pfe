package com.dalal.boukingandreviewservicepfe.services;

import com.dalal.boukingandreviewservicepfe.dtos.response.ProviderStatsResponse;
import com.dalal.boukingandreviewservicepfe.dtos.request.ReviewCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ClientReviewHistoryResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ProviderDashboardSatisfactionResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    // 1. Ajouter un avis / Vote
    ReviewResponse createReview(ReviewCreateRequest request);

    // 2. Client : Lister ses propres recommendations/avis
    Page<ClientReviewHistoryResponse> getClientReviewHistory(Long clientId, Pageable pageable);

    // 3. Visiteur/Client : Consulter la note globale et les avis d'un prestataire
    Page<ReviewResponse> getProviderReviews(Long providerId,Pageable pageable);

    // 4. Prestataire: Consulter son tableau de bord de satisfaction
    ProviderDashboardSatisfactionResponse getProviderSatisfactionDashboard(Long providerId);
    ProviderStatsResponse getProviderStats(Long providerId);
}
