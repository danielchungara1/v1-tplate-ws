INSERT INTO role_permission(role_id, permission_id)
VALUES ((select id from role where name = 'ADMIN'), (select id from permission where name = 'CREATE_USERS')),
       ((select id from role where name = 'ADMIN'), (select id from permission where name = 'READ_USERS')),
       ((select id from role where name = 'ADMIN'), (select id from permission where name = 'UPDATE_USERS')),
       ((select id from role where name = 'ADMIN'), (select id from permission where name = 'DELETE_USERS'));