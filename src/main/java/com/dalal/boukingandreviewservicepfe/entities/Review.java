package com.dalal.boukingandreviewservicepfe.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recommandation", nullable = false)
    private Boolean isRecommended;

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String comment;

    @CreationTimestamp
    @Column(name = "date_publication", updatable = false)
    private LocalDateTime datePublication;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation", nullable = false, unique = true)
    private Reservation reservation;
}