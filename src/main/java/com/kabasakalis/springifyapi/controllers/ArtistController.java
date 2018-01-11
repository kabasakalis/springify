
package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.assemblers.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.GenreResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.PagedCustomResourcesAssembler;
import com.kabasakalis.springifyapi.domain.Album;
import com.kabasakalis.springifyapi.domain.Artist;
import com.kabasakalis.springifyapi.domain.BaseEntity;
import com.kabasakalis.springifyapi.domain.Genre;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RepositoryRestController
@RequestMapping("/artists")
public class ArtistController extends AbstractBaseRestController<Artist>{


    @Autowired
    @Qualifier("artistRepository")

    private ArtistRepository repository;
    private PagedCustomResourcesAssembler<Album> pagedAlbumAssembler;
    private AlbumResourceAssembler albumResourceAssembler;
    private GenreResourceAssembler genreResourceAssembler;
    private AlbumRepository albumRepository;
    private GenreRepository genreRepository;


    @Autowired
    public ArtistController(ArtistRepository repository,
                            AlbumResourceAssembler albumResourceAssembler,
                            AlbumRepository albumRepository,
                            ApplicationContext appContext,
                            GenreResourceAssembler genreResourceAssembler,
                            GenreRepository genreRepository,
                            ArtistResourceAssembler assembler) {
        super(repository, appContext, assembler);
        this.albumRepository = albumRepository;
        this.genreResourceAssembler = genreResourceAssembler;
        this.albumResourceAssembler = albumResourceAssembler;
        this.genreRepository = genreRepository;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedAlbumAssembler = new PagedCustomResourcesAssembler<Album>(resolver, null);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/albums",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getAlbums(
            Pageable pageRequest,
            @PathVariable Long id) {
        Page<Album> pagedAlbumsByArtistId = albumRepository.findAllByArtistId(id, pageRequest);
        return getAssociatedResources(albumResourceAssembler, pagedAlbumAssembler, pagedAlbumsByArtistId, pageRequest);
    }


    @RequestMapping(
            method = {RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.POST},
            path = "/{id}/albums",
            consumes = {"application/json", "text/uri-list"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<? extends ResourceSupport> addAlbumAssociations(
            @PathVariable long id,
            @RequestBody(required = false) Resources<? extends BaseEntity> albumLinks) {
        return associateResources(Association.ONE_TO_MANY,Album.class, albumRepository, id, albumLinks);
    }


    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{id}/albums/{albumId}",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity deleteAlbumAssociation(
            @PathVariable Long id, @PathVariable Long albumId) {
        return deleteAssociation(Association.ONE_TO_MANY,Album.class, albumRepository, id, albumId);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/genre",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> getGenre(
            @PathVariable Long id) {
        return getAssociatedResource(id, Genre.class, genreResourceAssembler);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/findByName",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<ResourceSupport> getArtistsByNameLike(@RequestParam("name") String name, Pageable pageRequest) {
                PagedResources<ResourceSupport> pagedResources = pagedAssembler.toResource(
                repository.findByNameIgnoreCaseContaining(name, pageRequest),
                assembler);

        assembler.addLinks(pagedResources);
        return new ResponseEntity(pagedResources, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/findByCountry",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<ResourceSupport> getArtistsByCountryLike(@RequestParam("country") String country, Pageable pageRequest) {
        PagedResources<ResourceSupport> pagedResources = pagedAssembler.toResource(
                repository.findByCountryIgnoreCaseContaining(country, pageRequest),
                assembler);
        assembler.addLinks(pagedResources);
        return new ResponseEntity(pagedResources, HttpStatus.OK);

    }

}
