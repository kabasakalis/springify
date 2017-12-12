--liquibase formatted sql
--changeset kabasakalis:010
create table albums (
    id int primary key,
    artist_id int REFERENCES artists(id),
    title varchar(255),
    year varchar(255),
    -- createdDate varchar(255),
    -- updatedDate varchar(255)
    created_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
    updated_date timestamp without time zone NOT NULL DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);
CREATE INDEX artist_idx ON albums (artist_id);
--rollback drop table albums;




