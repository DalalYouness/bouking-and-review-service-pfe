package com.dalal.boukingandreviewservicepfe.entities;

import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_rdv", nullable = false)
    private LocalDateTime dateRdv;

    @Column(name = "duree_reel")
    private Integer dureeReel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;

    /* Logical Foreign Keys */
    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Column(name = "id_provider", nullable = false)
    private Long idProvider;

    @Column(name = "id_service", nullable = false)
    private Long idService;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Review review;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}