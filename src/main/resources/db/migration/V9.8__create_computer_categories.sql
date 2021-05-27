INSERT INTO category( name, description, title, category_parent_id)
values ('COMPUTERS', 'COMPUTERS', 'COMPUTERS', NULL);

INSERT INTO category( name, description, title, category_parent_id)
values ('PROCESSORS', 'PROCESSORS', 'PROCESSORS', (select id from category where name = 'COMPUTERS'));

INSERT INTO category( name, description, title, category_parent_id)
values ('HIGH-R PROCESSORS', 'HIGH-R PROCESSORS', 'HIGH-R PROCESSORS', (select id from category where name = 'PROCESSORS'));

INSERT INTO category( name, description, title, category_parent_id)
values ('MIDDLE-R PROCESSORS', 'MIDDLE-R PROCESSORS', 'MIDDLE-R PROCESSORS', (select id from category where name = 'PROCESSORS'));
