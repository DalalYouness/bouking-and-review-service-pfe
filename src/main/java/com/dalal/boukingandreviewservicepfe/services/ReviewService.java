package com.dalal.boukingandreviewservicepfe.services;

import com.dalal.boukingandreviewservicepfe.dtos.request.ProviderStatsResponse;
import com.dalal.boukingandreviewservicepfe.dtos.request.ReviewCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ClientReviewHistoryResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ProviderDashboardSatisfactionResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    // 1. Ajouter un avis / Vote
    ReviewResponse createReview(ReviewCreateRequest request);

    // 2. Client : Lister ses propres recommendations/avis
    List<ClientReviewHistoryResponse> getClientReviewHistory(Long clientId);

    // 3. Visiteur/Client : Consulter la note globale et les avis d'un prestataire
    List<ReviewResponse> getProviderReviews(Long providerId);

    // 4. Prestataire: Consulter son tableau de bord de satisfaction
    ProviderDashboardSatisfactionResponse getProviderSatisfactionDashboard(Long providerId);
    ProviderStatsResponse getProviderStats(Long providerId);
}
