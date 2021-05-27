INSERT INTO category( name, description, title, category_parent_id)
values ('DRESS', 'DRESS', 'DRESS', NULL);

INSERT INTO category( name, description, title, category_parent_id)
values ('FOOTWEAR', 'FOOTWEAR', 'FOOTWEAR', (select id from category where name = 'DRESS'));
INSERT INTO category( name, description, title, category_parent_id)
values ('T-SHIRTS', 'T-SHIRTS', 'T-SHIRTS', (select id from category where name = 'DRESS'));