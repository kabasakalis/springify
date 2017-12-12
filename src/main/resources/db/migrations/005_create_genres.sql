--liquibase formatted sql
--changeset kabasakalis:005
create table genres (
    id int primary key,
    name varchar(255),
    created_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    updated_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
    -- createdDate varchar(255),
    -- updatedDate varchar(255)
);
--rollback drop table genres;
