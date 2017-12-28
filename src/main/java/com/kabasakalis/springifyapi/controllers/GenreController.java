

package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.GenreResource;
import com.kabasakalis.springifyapi.hateoas.GenreResourceAssembler;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.BaseEntity;
import com.kabasakalis.springifyapi.models.Genre;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;


@RepositoryRestController
@RequestMapping("/genres")
public class GenreController extends AbstractBaseRestController<Genre> {


    @Autowired
    private GenreResourceAssembler assembler;

//    @Qualifier("artistRepository")
    private GenreRepository genreRepository;
    private PagedResourcesAssembler<Artist> pagedArtistAssembler;
    private ArtistResourceAssembler artistResourceAssembler;
    private GenreResourceAssembler genreResourceAssembler;
    private ArtistRepository artistRepository;


    @Autowired
    public GenreController(GenreRepository repository,
                           ArtistRepository artistRepository,

                           ApplicationContext appContext,
                           GenreResourceAssembler assembler) {
        super(repository,appContext, assembler);
        this.artistRepository = artistRepository;
    }

    @RequestMapping(
            method = {RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.POST},
            path = "/{id}/artists",
            consumes = {"application/json", "text/uri-list"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<? extends ResourceSupport> addGenreArtists(
            @PathVariable long id,
            @RequestBody(required = false) Resources<? extends BaseEntity> artistLinks) {
        return addOneToManyResources(artistRepository, id, artistLinks);
    }


}
