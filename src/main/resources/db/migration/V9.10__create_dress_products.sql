INSERT INTO product (name, description, title, brand_id, category_id)
VALUES ('Racer Running Shoe', 'Racer Running Shoe', 'Racer Running Shoe', (select id from brand where name = 'ADIDAS'), (select id from category where name = 'FOOTWEAR')),
       ('Dry Tee', 'Dry Tee', 'Dry Tee', (select id from brand where name = 'NIKE'), (select id from category where name = 'T-SHIRTS'));

