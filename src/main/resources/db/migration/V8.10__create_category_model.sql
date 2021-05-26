CREATE TABLE category
(
    id BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255)        NOT NULL,
    title VARCHAR(255)        NOT NULL,
    category_parent_id       BIGINT DEFAULT NULL
);

ALTER TABLE category
    ADD FOREIGN KEY (category_parent_id) REFERENCES category (id);