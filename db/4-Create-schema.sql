-- NOTE: Execute when connected to database pr
\connect pr

SET ROLE prowner;

-- Create pr schema

CREATE SCHEMA IF NOT EXISTS pr;

-- Create tables

--Create table USER
CREATE TABLE IF NOT EXISTS pr.user
(
	id bigint NOT NULL,
	fullname text,
	email text,
	username text,
	password text,
	status text,
	CONSTRAINT pk_user_id 
        PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pr_ts01;

ALTER TABLE pr.user
    OWNER to prowner;
	
CREATE SEQUENCE IF NOT EXISTS pr.pr_user_id_seq
    INCREMENT 1
    START 1;	


--Create table INSTITUTE
CREATE TABLE IF NOT EXISTS pr.institute
(
	id bigint NOT NULL,
	adress text,
	city text,
	name text,
	img_cover_src text,
	CONSTRAINT pk_institute_id 
        PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pr_ts01;

ALTER TABLE pr.institute
    OWNER to prowner;
	
CREATE SEQUENCE IF NOT EXISTS pr.pr_institute_id_seq
    INCREMENT 1
    START 1;	
	

--Create table LECTURER
CREATE TABLE IF NOT EXISTS pr.lecturer
(
	id bigint NOT NULL,
	fullname text,
	email text,
	img_src text,
	institute_id bigint,
	CONSTRAINT pk_lecturer_id 
        PRIMARY KEY (id),
	CONSTRAINT fk_institute_id
		FOREIGN KEY (institute_id)
		REFERENCES pr.institute (id) MATCH SIMPLE
		ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pr_ts01;

ALTER TABLE pr.lecturer
    OWNER to prowner;
	
CREATE SEQUENCE IF NOT EXISTS pr.pr_lecturer_id_seq
    INCREMENT 1
    START 1;	
	

--Create table Review
CREATE TABLE IF NOT EXISTS pr.review
(
	id bigint NOT NULL,
	user_id bigint NOT NULL,
	lecturer_id bigint NOT NULL,
	useful_l integer,
	interesting_l integer,
	material integer,
	commitment integer,
	communication integer,
	comment text,
	date text,
	CONSTRAINT pk_review_id 
		PRIMARY KEY (id),
    CONSTRAINT fk_review_user_id 
        FOREIGN KEY (user_id)
        REFERENCES pr.user (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_review_lecturer_id 
        FOREIGN KEY (lecturer_id)
        REFERENCES pr.lecturer (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pr_ts01;

ALTER TABLE pr.review
    OWNER to prowner;
	
CREATE SEQUENCE IF NOT EXISTS pr.pr_review_id_seq
    INCREMENT 1
    START 1;	
	

--Create table Review Like
CREATE TABLE IF NOT EXISTS pr.review_like
(
	user_id bigint NOT NULL,
	review_id bigint NOT NULL,
	like_ boolean,
	dislike_ boolean,
	CONSTRAINT pk_review_like_id 
		PRIMARY KEY (user_id, review_id),
    CONSTRAINT fk_review_like_user_id 
        FOREIGN KEY (user_id)
        REFERENCES pr.user (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_review_like_review_id 
        FOREIGN KEY (review_id)
        REFERENCES pr.review (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pr_ts01;

ALTER TABLE pr.review_like
    OWNER to prowner;
	
CREATE INDEX ix_review_like_revid ON pr.review_like (user_id, review_id);
