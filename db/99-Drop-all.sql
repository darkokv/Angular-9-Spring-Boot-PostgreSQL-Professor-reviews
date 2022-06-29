-- NOTE: Execute when connected to database pr as postgres user

\connect pr

SET ROLE postgres;

-- 1 - Drop database

-- Check connections & close all clients
SELECT * FROM pg_stat_activity WHERE datname = 'pr';

-- drop DB
\connect postgres
DROP DATABASE IF EXISTS pr;

-- 2 - Drop users

DROP OWNED BY prowner CASCADE;
REASSIGN OWNED BY prowner to postgres;
DROP ROLE IF EXISTS prowner;

-- 3 - Drop tablespace

DROP TABLESPACE IF EXISTS pr_ts01;

-- 4 - Drop schema

DROP SCHEMA IF EXISTS pr CASCADE;
