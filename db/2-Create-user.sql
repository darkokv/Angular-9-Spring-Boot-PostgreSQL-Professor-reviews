-- NOTE: Execute when connected to database pr
\connect pr

-- Set up user/

DO $$
BEGIN
	IF NOT EXISTS (
		SELECT 
		FROM pg_catalog.pg_roles 
		WHERE rolname='prowner') THEN

		CREATE ROLE prowner WITH
		  LOGIN
		  NOSUPERUSER
		  INHERIT
		  NOCREATEDB
		  NOCREATEROLE
		  REPLICATION
		  CONNECTION LIMIT 100
		  UNENCRYPTED  PASSWORD 'professoReviews';
  	END IF;
END $$;

GRANT CREATE ON DATABASE pr TO prowner;
