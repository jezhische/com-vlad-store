-- CREATE DATABASE store
-- WITH OWNER = postgres
-- ENCODING = 'UTF8'
-- TABLESPACE = pg_default
-- LC_COLLATE = 'Russian_Russia.1251'
-- LC_CTYPE = 'Russian_Russia.1251'
-- CONNECTION LIMIT = -1;
-- GRANT CONNECT, TEMPORARY ON DATABASE textsaver TO public;
-- GRANT ALL ON DATABASE textsaver TO postgres WITH GRANT OPTION;

--insert into roles values (1, 'CUSTOMER');
--insert into roles values (2, 'ADMIN');

-- select login, password from customers where login='jezhische'

-- select c.login, r.role from customers c inner join customer_role cr on c.id = cr.customer_id inner join roles r on r.id = cr.role_id where c.login = 'jezhische'

