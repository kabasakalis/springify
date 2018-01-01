package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "playlists", path = "playlists")
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findAllByAlbums(Album album, Pageable pageable);
    boolean getPlaylistByIdExists(Long id);
}

