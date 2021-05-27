INSERT INTO "user" (name, lastname, email, phone, username, password, role_id)
VALUES ('Tester',
        'Tester',
        'danielchungara100@gmail.com',
        '1144367326',
        'tester',
        '$2a$12$YZT.gCHztl/9T5DQ2rhY/e2waamiyFrbE7LWHTM5FObI8HZpMMkGm',
        (select id from role where name = 'TESTER'));