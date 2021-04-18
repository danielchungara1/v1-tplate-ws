INSERT INTO users (role_id, contact_id, credentials_id)
VALUES ((select id from role where name = 'ADMIN'),
        (select id from contact where email = 'danielchungara1@gmail.com'),
        (select id from credentials where username = 'administrador'));