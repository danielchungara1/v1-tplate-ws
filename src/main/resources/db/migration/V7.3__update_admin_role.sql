INSERT INTO role_permission(role_id, permission_id)
VALUES ((select id from role where name = 'ADMIN'), (select id from permission where name = 'UPDATE_ROLES'));