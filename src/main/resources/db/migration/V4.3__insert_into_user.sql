INSERT INTO "user" (name, lastname, email, phone, username, password, role_id)
VALUES ('Admin',
        'Admin',
        'danielchungara1@gmail.com',
        '1144367326',
        'administrador',
        '$2a$12$IIhOae9Fq.1s8oZh4BSPJOqo4CKH1IeWdFHkU59Ns4x/CfE8sK6yO',
        (select id from role where name = 'ADMIN'));

