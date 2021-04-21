CREATE TABLE role_permission
(
    id BIGSERIAL PRIMARY KEY,
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    CONSTRAINT unique_role_id_permission_id UNIQUE (role_id, permission_id)
);