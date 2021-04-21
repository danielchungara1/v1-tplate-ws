CREATE TABLE password_recovery
(
    id BIGSERIAL PRIMARY KEY,
    code            VARCHAR(255) NOT NULL,
    expiration_date TIMESTAMP    NOT NULL
);