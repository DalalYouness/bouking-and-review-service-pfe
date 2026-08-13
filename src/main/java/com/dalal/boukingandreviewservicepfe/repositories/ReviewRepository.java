package com.dalal.boukingandreviewservicepfe.repositories;

import com.dalal.boukingandreviewservicepfe.entities.Reservation;
import com.dalal.boukingandreviewservicepfe.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    /*start consulter la notes global et les avis */
    Page<Review> findByReservationIdProvider(Long idProvider, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.reservation.idProvider = :idProvider")
    long countTotalReviewsByProviderId(@Param("idProvider") Long idProvider);
    @Query("SELECT COUNT(r) FROM Review r WHERE r.reservation.idProvider = :idProvider AND r.isRecommended = true")
    long countPositiveReviewsByProviderId(@Param("idProvider") Long idProvider);
    /*end consulter la notes global et les avis */
    // we will need it for voting providers
    boolean existsByReservationId(Long idReservation);

    // need it for consulting all client's recommendations
    Page<Review> findByReservationIdClient(Long idClient, Pageable pageable);
}
