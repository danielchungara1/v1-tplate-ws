ALTER TABLE USERS
    ADD NAME VARCHAR(255) DEFAULT NULL,
    ADD LASTNAME VARCHAR(255) DEFAULT NULL,
    ADD EMAIL VARCHAR(255) UNIQUE DEFAULT NULL,
    ADD TELEFONO VARCHAR(255) DEFAULT NULL;