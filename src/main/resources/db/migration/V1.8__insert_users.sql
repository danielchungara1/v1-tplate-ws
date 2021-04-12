INSERT INTO USERS (USERNAME, PASSWORD, ROL_ID, NAME, LASTNAME, EMAIL, PHONE)
VALUES ('administrador',
        '$2a$12$IIhOae9Fq.1s8oZh4BSPJOqo4CKH1IeWdFHkU59Ns4x/CfE8sK6yO',
        (SELECT ID FROM ROLES WHERE NAME = 'ADMIN'),
        'Admin',
        'Admin',
        'danielchungara1@gmail.com',
        '1144367326');