package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.domain.Album;
import com.kabasakalis.springifyapi.domain.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "playlists", path = "playlists")
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findAllByAlbums(Album album, Pageable pageable);
}

