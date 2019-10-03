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

-- DROP FUNCTION delete_all_product_details_by_product_id_return_count(bigint);

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

-- -----------------------------------------------------------------------------------------------------------------
--   details product_details%ROWTYPE;
--   p_ids BIGINT;
--   pnp CHARACTER;

--   FOR p IN SELECT pd FROM product_details AS pd WHERE product_id IN (SELECT p.id FROM products AS p  WHERE p.name LIKE)

--   Вместо LIKE можно использовать ключевое слово ILIKE, чтобы поиск был регистр-независимым с учётом текущей языковой среды
--   https://postgrespro.ru/docs/postgresql/9.6/functions-matching
--   pnp := CAST($1 AS CHARACTER);

-- DROP FUNCTION find_all_products_by_product_name_part_order_by_data(VARCHAR);

CREATE OR REPLACE FUNCTION find_all_products_by_product_name_part_order_by_datetime(IN product_name_part VARCHAR) RETURNS SETOF products AS
'
DECLARE
BEGIN

  RETURN query (SELECT * FROM products AS p  WHERE p.name ILIKE CONCAT($$%$$, $1, $$%$$));
END
'
  LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------------------------------------------
-- DROP FUNCTION find_distinct_product_images_id_by_product_name_part_order_by_data(VARCHAR);

CREATE OR REPLACE FUNCTION find_distinct_product_images_id_by_product_name_part_order_by_datetime(IN product_name_part VARCHAR) RETURNS SETOF BIGINT AS
'
DECLARE
BEGIN

  RETURN query (SELECT DISTINCT pi.id FROM product_images pi
                             INNER JOIN product_detail_product_image pd_pi on pi.id = pd_pi.product_image_id
                             INNER JOIN product_details pd ON pd_pi.product_detail_id = pd.id
                             INNER JOIN products p ON pd.product_id = p.id WHERE p.name ILIKE CONCAT($$%$$, $1, $$%$$));
END
'
  LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------------------------------------------
-- DROP TYPE product_pimage;

CREATE TYPE product_pimage AS (
  p_id BIGINT,
  p_name VARCHAR,
  p_producer_id BIGINT,
  pimage_id BIGINT,
  pimage_name VARCHAR,
  pimage_data OID
--   pimage_data BYTEA
  );

DROP FUNCTION find_product_product_images_id_by_product_name_part(VARCHAR);

CREATE OR REPLACE FUNCTION find_product_product_images_id_by_product_name_part(IN product_name_part VARCHAR) RETURNS SETOF product_pimage AS
'
DECLARE
  _result product_pimage;
BEGIN

  FOR _result.p_id, _result.p_name, _result.p_producer_id, _result.pimage_id, _result.pimage_name, _result.pimage_data IN
    SELECT DISTINCT p.id, p.name, p.producer_id, pi.id, pi.file_name, pi.data
                    FROM product_images pi
                             INNER JOIN product_detail_product_image pd_pi on pi.id = pd_pi.product_image_id
                             INNER JOIN product_details pd ON pd_pi.product_detail_id = pd.id
                             INNER JOIN products p ON pd.product_id = p.id WHERE p.name ILIKE CONCAT($$%$$, $1, $$%$$)
  LOOP
    RETURN NEXT _result;
  END LOOP;
  RETURN;
END
'
  LANGUAGE plpgsql;

-- ------------------------------------------------------------------------------------------------------------------


-- ========================================================================================== test queries
-- select c.login, r.role from customers c inner join customer_role cr on c.id = cr.customer_id inner join roles r on r.id = cr.role_id where c.login = ?
-- SELECT * delete_all_product_details_by_product_id_return_count(pid) where pid in(SELECT p.id FROM products as p where p.name = 'product_p');


SELECT * FROM find_all_products_by_product_name_part_order_by_datetime('estPW');

SELECT DISTINCT pi.id FROM product_images pi
                             INNER JOIN product_detail_product_image pd_pi ON pi.id = pd_pi.product_image_id
                             INNER JOIN product_details pd ON pd_pi.product_detail_id = pd.id
                             INNER JOIN products p ON pd.product_id = p.id WHERE p.name ILIKE '%stPW%';

SELECT * FROM find_distinct_product_images_id_by_product_name_part_order_by_datetime('estPW');

SELECT * FROM find_product_product_images_id_by_product_name_part('test');




