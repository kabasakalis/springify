--liquibase formatted sql
--changeset kabasakalis:002
insert into artists (id, name, country) values (1, 'King Diamond', 'Denmark');
insert into artists (id, name, country) values (2, 'Iron Maiden', 'UK');
insert into artists (id, name, country) values (3, 'Judas Priest', 'UK');
insert into artists (id, name, country) values (4, 'Savatage', 'USA');
insert into artists (id, name, country) values (5, 'Post Malone', 'USA');

--rollback delete from  artists where id in (1,2,3, 4,5)
