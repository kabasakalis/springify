--liquibase formatted sql
--changeset kabasakalis:001
create table artists (
    id int primary key,
    name varchar(255)
);
--rollback drop table artists;
