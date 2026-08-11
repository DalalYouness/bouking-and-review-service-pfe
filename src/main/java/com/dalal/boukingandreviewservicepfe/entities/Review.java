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

    @Column(name = "recommandation")
    private Boolean isRecommended;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime publishAt;

    // je pense que c'est pas logique que une fois la review supprimer la reservation supprimé
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;
}
