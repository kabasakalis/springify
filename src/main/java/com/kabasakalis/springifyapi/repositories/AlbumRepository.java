package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findAllByArtistId(Long artistId, Pageable pageable);

    Page<Album> findAllByPlaylists(Playlist playlist, Pageable pageable);
}
