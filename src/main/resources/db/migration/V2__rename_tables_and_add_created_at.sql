RENAME TABLE reservation TO reservations;
RENAME TABLE review TO reviews;

ALTER TABLE reservations
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
