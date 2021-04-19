CREATE TABLE contact
(
    id BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) DEFAULT NULL,
    lastname VARCHAR(255) DEFAULT NULL,
    phone    VARCHAR(255) DEFAULT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
);