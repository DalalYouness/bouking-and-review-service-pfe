package com.dalal.boukingandreviewservicepfe.entities;

import com.dalal.boukingandreviewservicepfe.enums.BookingStatus;
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
@Table(name = "reservations")
public class Reservation  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "date_rdv")
    private LocalDateTime dateRdv;

    @Column(name = "duree_reel")
    private Integer dureeReel;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    /* start logical FKs */
    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "id_provider")
    private Long idProvider;

    @Column(name = "id_service")
    private Long idService;
    /* end logical FKs */

    @Column(name = "date_publication",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "commentaire")
    private String comment;

    @OneToOne(mappedBy = "id_reservation")
    private Review review;

}
