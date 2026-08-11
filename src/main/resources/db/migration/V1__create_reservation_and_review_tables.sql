CREATE TABLE reservation (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        date_rdv DATETIME NOT NULL,
        duree_reel INT DEFAULT 0,
        status VARCHAR(20) DEFAULT 'EN_ATTENTE',

    -- Logical Foreign Keys
        id_client BIGINT NOT NULL,
        id_provider BIGINT NOT NULL,
        id_service BIGINT NOT NULL,

    -- B-Tree Indexes Optimization
        INDEX (id_client),
        INDEX (id_provider),
        INDEX (id_service),

    -- Prevention of Double Booking
    -- Composite Unique Constraint
    -- TODO (Post-MVP): Currently, we prevent double booking only at the exact exact same timestamp (date_rdv).
    -- TODO (Post-MVP): In future versions (e.g., V2), add 'date_fin_rdv' or application-level time-slot overlap
    -- validation (start_time < new_end AND end_time > new_start) to block the whole duration.
        UNIQUE (id_provider, date_rdv)
) ENGINE=InnoDB;


CREATE TABLE review (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        recommandation BOOLEAN NOT NULL,
        commentaire TEXT,
        date_publication TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Physical Foreign Key (1:1 Relationship)
        id_reservation BIGINT NOT NULL UNIQUE,

        CONSTRAINT fk_review_reservation
        FOREIGN KEY (id_reservation) REFERENCES reservation(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;