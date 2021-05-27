INSERT INTO product (name, description, title, brand_id, category_id)
VALUES ('i7-9700F', 'i7-9700F', 'i7-9700F', (select id from brand where name = 'INTEL'), (select id from category where name = 'HIGH-R PROCESSORS')),
       ('i5-9400', 'i5-9400', 'i5-9400', (select id from brand where name = 'INTEL'), (select id from category where name = 'MIDDLE-R PROCESSORS'));

INSERT INTO product (name, description, title, brand_id, category_id)
VALUES ('Ryzen 7 2700', 'Ryzen 7 2700', 'Ryzen 7 2700', (select id from brand where name = 'AMD'), (select id from category where name = 'HIGH-R PROCESSORS')),
       ('Ryzen 5 3600', 'Ryzen 5 3600', 'Ryzen 5 3600', (select id from brand where name = 'AMD'), (select id from category where name = 'MIDDLE-R PROCESSORS'));