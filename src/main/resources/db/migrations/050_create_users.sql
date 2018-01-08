--liquibase formatted sql
--changeset kabasakalis:050
create table springifyUsers (
    id serial primary key,
    username varchar(255),
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    password_confirm varchar(255) NOT NULL,
    created_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    updated_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);
ALTER SEQUENCE users_id_seq RESTART WITH 100;
--rollback drop table springifyUsers;
