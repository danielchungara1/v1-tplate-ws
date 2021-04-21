CREATE TABLE "user"
(
    id BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) DEFAULT NULL,
    lastname VARCHAR(255) DEFAULT NULL,
    phone    VARCHAR(255) DEFAULT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    password_recovery_id BIGINT DEFAULT NULL UNIQUE,
    role_id              BIGINT NOT NULL
);