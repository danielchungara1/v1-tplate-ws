INSERT INTO role_permission(role_id, permission_id)
VALUES ((select id from role where name = 'VISUALIZER'), (select id from permission where name = 'READ_PRODUCTS')),
       ((select id from role where name = 'VISUALIZER'), (select id from permission where name = 'READ_CATEGORIES'));

