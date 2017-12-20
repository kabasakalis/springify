--liquibase formatted sql
--changeset kabasakalis:010
create table artists (
    id serial primary key,
    genre_id int REFERENCES genres(id),
    name varchar(255),
    country varchar(255),
    created_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    updated_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);
ALTER SEQUENCE artists_id_seq RESTART WITH 1000;
--rollback drop table artists;
