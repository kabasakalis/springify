package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
//@Repository("artistRepository")
//public interface ArtistRepository extends JpaRepository<Artist, Long> {
//
//}


@RepositoryRestResource(collectionResourceRel = "artists", path = "artists")
public interface ArtistRepository extends JpaRepository<Artist, Long> {
  Optional<Artist> findById(Long id);

}
