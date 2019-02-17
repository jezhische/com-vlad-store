 CREATE DATABASE store
 WITH OWNER = postgres
 ENCODING = 'UTF8'
 TABLESPACE = pg_default
 LC_COLLATE = 'Russian_Russia.1251'
 LC_CTYPE = 'Russian_Russia.1251'
 CONNECTION LIMIT = -1;
 GRANT CONNECT, TEMPORARY ON DATABASE textsaver TO public;
 GRANT ALL ON DATABASE textsaver TO postgres WITH GRANT OPTION;