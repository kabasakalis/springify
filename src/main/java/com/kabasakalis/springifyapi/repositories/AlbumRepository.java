package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("albumRepository")
public interface AlbumRepository extends JpaRepository<Album, Long> {

}
