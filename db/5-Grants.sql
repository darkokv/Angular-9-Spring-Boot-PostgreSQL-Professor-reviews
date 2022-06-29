-- NOTE: Execute when connected to database pr
\connect pr

SET ROLE postgres;

-- Schema DB administrator

GRANT ALL ON SCHEMA pr TO prowner;
ALTER USER prowner SET search_path TO pr,public;
