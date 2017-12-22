package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

//@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
//@Repository("artistRepository")
//public interface ArtistRepository extends JpaRepository<Artist, Long> {
//
//}

@Transactional
@RepositoryRestResource(collectionResourceRel = "artists", path = "artists")
public interface ArtistRepository extends JpaRepository<Artist, Long> {
  Optional<Artist> findById(Long id);
  Optional<Artist> findArtistById(Long id);
  void  deleteById(Long id);
  // Artist addArtist(long genreId, String name, String country);
 Page<Artist> findAllByGenreId(Long id, Pageable pageable);
}
