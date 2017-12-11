--liquibase formatted sql
--changeset kabasakalis:050
create table users (
    id int primary key,
    name varchar(255),
    email varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    token varchar(255),
    created_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    updated_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);
--rollback drop table users;
