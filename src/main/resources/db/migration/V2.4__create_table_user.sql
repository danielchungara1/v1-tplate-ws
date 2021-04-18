CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    contact_id           BIGINT NOT NULL,
    credentials_id       BIGINT NOT NULL,
    password_recovery_id BIGINT DEFAULT NULL,
    role_id              BIGINT NOT NULL
);