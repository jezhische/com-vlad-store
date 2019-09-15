-- CREATE DATABASE store
-- WITH OWNER = postgres
-- ENCODING = 'UTF8'
-- TABLESPACE = pg_default
-- LC_COLLATE = 'Russian_Russia.1251'
-- LC_CTYPE = 'Russian_Russia.1251'
-- CONNECTION LIMIT = -1;
-- GRANT CONNECT, TEMPORARY ON DATABASE textsaver TO public;
-- GRANT ALL ON DATABASE textsaver TO postgres WITH GRANT OPTION;

-- -----------------------------------------------------------------------------------------------------------------

-- DROP FUNCTION find_all_product_details_by_product_id(bigint);

CREATE OR REPLACE FUNCTION find_all_product_details_by_product_id(IN pid BIGINT) RETURNS SETOF product_details AS
  '
BEGIN
  RETURN QUERY (SELECT * FROM product_details AS pds WHERE pds.product_id = pid);
END
'
  LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------------------------------------------

-- DROP FUNCTION delete_all_product_details_by_product_id_return_deleted(bigint);

-- delete all product_details entries by product_id and return a LIST of deleted entries
CREATE OR REPLACE FUNCTION delete_all_product_details_by_product_id_return_deleted(IN pid BIGINT) RETURNS SETOF product_details AS
'
BEGIN
  RETURN QUERY DELETE FROM product_details AS pds WHERE pds.product_id = pid RETURNING *;
END
'
  LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------------------------------------------

DROP FUNCTION delete_all_product_details_by_product_id_return_count(bigint);

-- delete all product_details entries by product_id and return a COUNT of deleted entries
CREATE OR REPLACE FUNCTION delete_all_product_details_by_product_id_return_count(IN pid BIGINT) RETURNS void AS
'
BEGIN
--   get result setof as "deleted" and select count from it
--   WITH deleted AS (DELETE FROM product_details AS pds WHERE pds.product_id = pid RETURNING *) SELECT count(*) FROM deleted;

--   NB: method must return VOID and I dont use RETURNING statement; then DELETE statement returns count of
  --   affected rows according to common SQL rules
  DELETE FROM product_details AS pds WHERE pds.product_id = pid;
END
'
  LANGUAGE plpgsql;

-- ========================================================================================== test queries

-- select c.login, c.password, c.enabled from customers c where c.login='jezhische'

-- select c.login, r.role from customers c inner join customer_role cr on c.id = cr.customer_id inner join roles r on r.id = cr.role_id where c.login = 'jezhische'

-- select * from customers c where c.login = 'jezhische';
--
--  select * from roles;
--
-- select c.id, c.enabled, c.login, c.password, r.role from customers c inner join customer_role cr on c.id = cr.customer_id
--   inner join roles r on cr.role_id = r.id where c.login = 'jezhische';
--
-- select c.id, c.enabled, c.login, c.password, r.role from customers c inner join customer_role cr on c.id = cr.customer_id
--   inner join roles r on cr.role_id = r.id where r.role = 'CUSTOMER';
--
--
-- create table if not exists test (
--   id bigint not null,
--   img bytea,
--   primary key (id)
-- )
SELECT p.id FROM products as p where p.name = 'product_p';
SELECT p FROM products as p where p.id = 67;
-- SELECT * delete_all_product_details_by_product_id_return_count(pid) where pid in(SELECT p.id FROM products as p where p.name = 'product_p');
