package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository("albumRepository")
public interface AlbumRepository extends JpaRepository<Album, Long> {
 Page<Album> findAllByArtistId(Long id, Pageable pageable);
}
