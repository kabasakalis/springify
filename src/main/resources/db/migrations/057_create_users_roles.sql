--liquibase formatted sql
--changeset kabasakalis:057

create table users_roles (
   user_id integer references users(id),
   role_id integer references roles(id),
   primary key(user_id, role_id)
);

--rollback drop table user_roles;