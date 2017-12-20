package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
//@Repository("artistRepository")
//public interface ArtistRepository extends JpaRepository<Artist, Long> {
//
//}


@RepositoryRestResource(collectionResourceRel = "genres", path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Long> {
  Optional<Genre> findById(Long id);
  // Artist addArtist(long genreId, String name, String country);
  //
}

