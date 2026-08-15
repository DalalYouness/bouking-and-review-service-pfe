package com.dalal.boukingandreviewservicepfe.services;

import com.dalal.boukingandreviewservicepfe.dtos.response.ProviderStatsResponse;
import com.dalal.boukingandreviewservicepfe.dtos.request.ReviewCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ClientReviewHistoryResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ProviderDashboardSatisfactionResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReviewResponse;
import com.dalal.boukingandreviewservicepfe.entities.Reservation;
import com.dalal.boukingandreviewservicepfe.entities.Review;
import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;
import com.dalal.boukingandreviewservicepfe.exceptions.InvalidReservationStateException;
import com.dalal.boukingandreviewservicepfe.exceptions.ResourceNotFoundException;
import com.dalal.boukingandreviewservicepfe.exceptions.ReviewAlreadyExistsException;
import com.dalal.boukingandreviewservicepfe.mappers.ReviewMapper;
import com.dalal.boukingandreviewservicepfe.repositories.ReservationRepository;
import com.dalal.boukingandreviewservicepfe.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    // local dependencies
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewMapper reviewMapper;

    // TODO: wi will need
    //       rest clients after + asynchronous services


    // Client : Voter un prestataire (+ Extend : Ajouter un avis textuel)
    @Override
    @Transactional
    public ReviewResponse createReview(ReviewCreateRequest request) {
        // 1. Validation (Guard Clause)
        if (request == null) {
            throw new IllegalArgumentException("Review create request cannot be null");
        }

        // 2. Business Validation
        Reservation reservation = reservationRepository.findById(request.reservationId())
                .orElseThrow(() -> ResourceNotFoundException.forReservation(request.reservationId()));

        if (reservation.getStatus() != BookingStatus.COMPLETED) {
            throw new InvalidReservationStateException("Cannot review a reservation that is not COMPLETED");
        }

        if (reviewRepository.existsByReservationId(request.reservationId())) {
            throw ReviewAlreadyExistsException.forReservation(request.reservationId());
        }

        // 3. Mapping: Request -> Entity
        Review review = reviewMapper.toEntity(request);
        review.setReservation(reservation);

        // 4. Persistence
        Review savedReview = reviewRepository.save(review);

        // TODO: Notify the provider via Kafka/Event that he has a new review

        // 5. Response Mapping
        return reviewMapper.toResponse(savedReview);
    }

    // Client : Lister ses propres recommendations
    @Override
    @Transactional(readOnly = true)
    public Page<ClientReviewHistoryResponse> getClientReviewHistory(Long clientId, Pageable pageable) {
        // 1. Validation (Guard Clause)
        if (clientId == null) {
            throw new IllegalArgumentException("Client id cannot be null");
        }

        // 2. Fetch Data with Pagination
        Page<Review> reviews = reviewRepository.findByReservationIdClient(clientId, pageable);

        // 3. Map Page<Entity> -> Page<DTO> with External Data Resolution
        return reviews.map(review -> {
            Reservation reservation = review.getReservation();

            // TODO: Call Identity-Service via OpenFeign to get real Provider Name
            String providerName = "Provider Name Placeholder";

            // TODO: Call Provider-Content-Service via OpenFeign to get real Service Name
            String serviceName = "Service Name Placeholder";

            return new ClientReviewHistoryResponse(
                    review.getId(),
                    reservation.getIdProvider(),
                    providerName,
                    serviceName,
                    review.getComment(),
                    review.getIsRecommended(),
                    review.getDatePublication()
            );
        });
    }

    // all users : Consulter la note globale et les avis / il retourne les avis
    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getProviderReviews(Long providerId, Pageable pageable) {
        // 1. Validation (Guard Clause)
        if (providerId == null) {
            throw new IllegalArgumentException("Provider id cannot be null");
        }

        // 2. Business Validation
        // TODO: Call identity-service via OpenFeign to verify provider existence

        // 3. Fetch Paginated Data
        Page<Review> reviews = reviewRepository.findByReservationIdProvider(providerId, pageable);

        // 4. Map to DTO with External Data Resolution
        return reviews.map(review -> {
            // TODO: Call identity-service via OpenFeign using review.getReservation().getIdClient() to get real client name
            String clientName = "Client Placeholder";

            return new ReviewResponse(
                    review.getId(),
                    clientName,
                    review.getComment(),
                    review.getIsRecommended(),
                    review.getDatePublication()
            );
        });
    }

    // all users : Consulter la note globale et les avis / il retourne la note global
    @Override
    @Transactional(readOnly = true)
    public ProviderStatsResponse getProviderStats(Long providerId) {
        // 1. Validation (Guard Clause)
        if (providerId == null) {
            throw new IllegalArgumentException("Provider id cannot be null");
        }

        // 2. Business Validation
        // TODO: Verify provider existence via identity-service (OpenFeign)

        // 3. Fetch Aggregate Data
        long totalReviews = reviewRepository.countTotalReviewsByProviderId(providerId);

        // 4. Handle Division by Zero Edge Case
        if (totalReviews == 0) {
            return ProviderStatsResponse.builder()
                    .providerId(providerId)
                    .totalClientsVotants(0L)
                    .tauxRecommandation(0.0)
                    .build();
        }

        // 5. Fetch Positive Reviews & Calculate Percentage
        long positiveReviews = reviewRepository.countPositiveReviewsByProviderId(providerId);
        double tauxRecommendation = (positiveReviews * 100.0) / totalReviews;

        return ProviderStatsResponse.builder()
                .providerId(providerId)
                .totalClientsVotants(totalReviews)
                .tauxRecommandation(tauxRecommendation)
                .build();
    }

    // Prestataire : Consulter son tableau de bord de satisfaction
    @Override
    @Transactional(readOnly = true)
    public ProviderDashboardSatisfactionResponse getProviderSatisfactionDashboard(Long providerId) {
        // 1. Validation (Guard Clause)
        if (providerId == null) {
            throw new IllegalArgumentException("Provider id cannot be null");
        }

        // 2. Business Validation
        // TODO: Verify provider existence via identity-service (OpenFeign)

        // 3. Aggregate Queries & Calculation
        long totalVotes = reviewRepository.countTotalReviewsByProviderId(providerId);

        if (totalVotes == 0) {
            return ProviderDashboardSatisfactionResponse.builder()
                    .providerId(providerId)
                    .tauxRecommendation(0.0)
                    .positiveVotesCount(0L)
                    .negativeVotesCount(0L)
                    .build();
        }

        long positiveVotes = reviewRepository.countPositiveReviewsByProviderId(providerId);
        long negativeVotes = totalVotes - positiveVotes;
        double tauxRecommendation = (positiveVotes * 100.0) / totalVotes;

        return ProviderDashboardSatisfactionResponse.builder()
                .providerId(providerId)
                .tauxRecommendation(tauxRecommendation)
                .positiveVotesCount(positiveVotes)
                .negativeVotesCount(negativeVotes)
                .build();
    }

}
