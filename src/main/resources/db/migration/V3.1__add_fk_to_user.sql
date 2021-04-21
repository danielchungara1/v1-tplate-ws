ALTER TABLE "user"
    ADD FOREIGN KEY (role_id) REFERENCES role (id),
    ADD FOREIGN KEY (password_recovery_id) REFERENCES password_recovery (id);