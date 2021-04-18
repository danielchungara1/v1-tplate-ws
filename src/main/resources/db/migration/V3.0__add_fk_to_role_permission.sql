ALTER TABLE role_permission
    ADD FOREIGN KEY (role_id) REFERENCES role (id),
    ADD FOREIGN KEY (permission_id) REFERENCES permission (id);