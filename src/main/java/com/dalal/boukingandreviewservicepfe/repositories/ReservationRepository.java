package com.dalal.boukingandreviewservicepfe.repositories;

import com.dalal.boukingandreviewservicepfe.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // 1. Consulter les demandes (Client) -> the third use case
    Page<Reservation> findByIdClient(Long idClient, Pageable pageable);

    // 2. Consulter les rendez-vous (Prestataire / Provider) -> the first use case
    Page<Reservation> findByIdProvider(Long idProvider, Pageable pageable);
}
