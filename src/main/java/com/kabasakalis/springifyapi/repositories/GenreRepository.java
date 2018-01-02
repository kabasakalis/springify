package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "genres", path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Long> { }

