INSERT INTO role_permission(role_id, permission_id)
VALUES ((select id from role where name = 'VISUALIZER'), (select id from permission where name = 'READ_USERS')),
       ((select id from role where name = 'VISUALIZER'), (select id from permission where name = 'READ_ROLES')),
       ((select id from role where name = 'VISUALIZER'), (select id from permission where name = 'READ_PERMISSIONS'));;