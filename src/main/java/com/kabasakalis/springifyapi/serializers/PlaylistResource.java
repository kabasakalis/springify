package com.kabasakalis.springifyapi.serializers;

import com.kabasakalis.springifyapi.domain.Playlist;

public class PlaylistResource extends BaseResourceSupport {

    public long id;
    public String name;
    public int albums_count;
    public String created_date;
    public String updated_date;

    public PlaylistResource(Playlist playlist) {
        super(playlist);
        id = playlist.getId();
        name = playlist.getName();
        albums_count = playlist.getAlbums().size();
        created_date = playlist.getFormattedCreatedDate();
        updated_date = playlist.getFormattedUpdatedDate();
    }

}
