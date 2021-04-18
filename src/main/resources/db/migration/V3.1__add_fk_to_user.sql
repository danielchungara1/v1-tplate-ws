ALTER TABLE users
    ADD FOREIGN KEY (role_id) REFERENCES role (id),
    ADD FOREIGN KEY (contact_id) REFERENCES contact (id),
    ADD FOREIGN KEY (credentials_id) REFERENCES credentials (id),
    ADD FOREIGN KEY (password_recovery_id) REFERENCES password_recovery (id);