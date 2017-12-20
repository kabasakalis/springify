--liquibase formatted sql
--changeset kabasakalis:050
create table users (
    id serial primary key,
    name varchar(255),
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    token varchar(255),
    created_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    updated_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);
ALTER SEQUENCE users_id_seq RESTART WITH 100;
--rollback drop table users;
