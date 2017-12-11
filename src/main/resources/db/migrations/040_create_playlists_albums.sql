--liquibase formatted sql
--changeset kabasakalis:040

create table playlists_albums (
   playlist_id integer references playlists(id),
   album_id integer references albums(id),
   primary key(playlist_id, album_id)
);

--rollback drop table playlists_albums;
