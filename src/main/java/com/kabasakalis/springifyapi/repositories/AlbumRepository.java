package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
 Page<Album> findAllByArtistId(Long artistId, Pageable pageable);
 Page<Album> findAllByPlaylists(Playlist playlist, Pageable pageable);
}
