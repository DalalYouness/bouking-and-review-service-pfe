package com.dalal.boukingandreviewservicepfe.services;

import com.dalal.boukingandreviewservicepfe.dtos.request.ProviderStatsResponse;
import com.dalal.boukingandreviewservicepfe.dtos.request.ReviewCreateRequest;
import com.dalal.boukingandreviewservicepfe.dtos.response.ClientReviewHistoryResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ProviderDashboardSatisfactionResponse;
import com.dalal.boukingandreviewservicepfe.dtos.response.ReviewResponse;
import com.dalal.boukingandreviewservicepfe.entities.Reservation;
import com.dalal.boukingandreviewservicepfe.entities.Review;
import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;
import com.dalal.boukingandreviewservicepfe.exceptions.DuplicateResourceException;
import com.dalal.boukingandreviewservicepfe.exceptions.InvalidReservationStateException;
import com.dalal.boukingandreviewservicepfe.exceptions.ResourceNotFoundException;
import com.dalal.boukingandreviewservicepfe.mappers.ReviewMapper;
import com.dalal.boukingandreviewservicepfe.repositories.ReservationRepository;
import com.dalal.boukingandreviewservicepfe.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    // local dependencies
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewMapper reviewMapper;

    // TODO: wi will need
    //       rest clients after + asynchronous services


    @Override
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
            throw new DuplicateResourceException("A review has already been submitted for this reservation");
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

    @Override
    public List<ClientReviewHistoryResponse> getClientReviewHistory(Long clientId) {
        // validation
        if(clientId == null) {
            throw new IllegalArgumentException("Client id cannot be null");
        }
        return List.of();
    }

    @Override
    public List<ReviewResponse> getProviderReviews(Long providerId) {
        return List.of();
    }

    @Override
    public ProviderDashboardSatisfactionResponse getProviderSatisfactionDashboard(Long providerId) {
        return null;
    }

    @Override
    public ProviderStatsResponse getProviderStats(Long providerId) {
        return null;
    }
}
