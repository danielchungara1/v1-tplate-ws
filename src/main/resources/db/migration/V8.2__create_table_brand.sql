CREATE TABLE brand
(
    id BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255)        NOT NULL,
    title       VARCHAR(255)        NOT NULL
);