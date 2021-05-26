CREATE TABLE product
(
    id BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255)        NOT NULL,
    title VARCHAR(255)        NOT NULL,
    brand_id       BIGINT DEFAULT NULL,
    category_id       BIGINT DEFAULT NULL
);

ALTER TABLE product
    ADD FOREIGN KEY (brand_id) REFERENCES brand (id),
    ADD FOREIGN KEY (category_id) REFERENCES category (id);