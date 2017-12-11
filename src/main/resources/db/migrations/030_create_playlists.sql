--liquibase formatted sql
--changeset kabasakalis:030
create table playlists (
    id int primary key,
    name varchar(255),
    created_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    updated_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);
--rollback drop table playlists;

