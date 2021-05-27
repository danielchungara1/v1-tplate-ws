INSERT INTO "user" (name, lastname, email, phone, username, password, role_id)
VALUES ('Visualizer',
        'Visualizer',
        'fchungara@gmail.com',
        '1144367326',
        'visualizer',
        '$2a$12$y8S6KBWiDfyhEsP7raxspunfnLVW.qFo3fM3.qKPZu8zrENM4peVy',
        (select id from role where name = 'VISUALIZER'));

