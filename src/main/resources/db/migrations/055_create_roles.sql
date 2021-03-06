--liquibase formatted sql
--changeset kabasakalis:055
create table roles (
    id serial primary key,
    name varchar(255),
    created_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    updated_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);
ALTER SEQUENCE roles_id_seq RESTART WITH 100;
--rollback drop table roles;
