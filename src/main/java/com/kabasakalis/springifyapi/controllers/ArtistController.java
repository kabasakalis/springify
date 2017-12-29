
package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.GenreResourceAssembler;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.BaseEntity;
import com.kabasakalis.springifyapi.models.Genre;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.repository.support.RepositoryInvokerFactory;
import org.springframework.data.rest.webmvc.ControllerUtils;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RepositoryRestController
@RequestMapping("/artists")
public class ArtistController extends AbstractBaseRestController<Artist> {


    @Autowired
    @Qualifier("artistRepository")

    private ArtistRepository repository;
    private PagedResourcesAssembler<BaseEntity> pagedAlbumAssembler;
    private SimpleIdentifiableResourceAssembler<BaseEntity> albumResourceAssembler;
    private GenreResourceAssembler genreResourceAssembler;
    private AlbumRepository albumRepository;


    @Autowired
    public ArtistController(ArtistRepository repository,
                            AlbumResourceAssembler albumResourceAssembler,
                             AlbumRepository albumRepository,
                            ApplicationContext appContext,
                            GenreResourceAssembler genreResourceAssembler,
                            ArtistResourceAssembler assembler) {
        super(repository,appContext, assembler);

        this.albumRepository = albumRepository;
        this.genreResourceAssembler = genreResourceAssembler;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedAlbumAssembler = new PagedResourcesAssembler<Album>(resolver, null);
    }

//    @RequestMapping(
//            method = RequestMethod.GET,
//            path = "/{id}/albums",
//            produces = MediaTypes.HAL_JSON_VALUE)
//    public ResponseEntity<Page<Album>> getArtistAlbums(
//            Pageable pageRequest,
//            @PathVariable Long id) {
//        PagedResources<Resource<Album>>
//                pagedArtistAlbums = pagedAlbumAssembler.toResource(
//                albumRepository.findAllByArtistId(id, pageRequest),
//                albumResourceAssembler);
//        return new ResponseEntity(pagedArtistAlbums, HttpStatus.OK);
//    }


    @RequestMapping(
            method = {RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.POST},
            path = "/{id}/albums",
            consumes = {"application/json", "text/uri-list"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<? extends ResourceSupport> addArtistAlbums(
            @PathVariable long id,
            @RequestBody(required = false) Resources<? extends BaseEntity> albumLinks) {
        return associateResources(Association.ONE_TO_MANY,albumRepository, id, albumLinks);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/albums",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> getArtistAlbums(
            Pageable pageRequest,
            @PathVariable Long id) {
        return  getAssociatedResources(
                "albums",
                albumRepository ,
                albumResourceAssembler,
                pagedAlbumAssembler,
                null,
                pageRequest);
    }


        @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/albums/{albumId}",
            produces = MediaTypes.HAL_JSON_VALUE)
            public ResponseEntity<?> getArtistAlbum(
            Pageable pageRequest,
            @PathVariable Long id) {
        return  getAssociatedResources(
                Association.ONE_TO_MANY,
                "albums",
                albumRepository ,
                albumResourceAssembler,
                null,
                albumId,
                null);
    }







    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/genre",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<Genre>> getGenre(@PathVariable Long id) {
        return Optional.ofNullable(repository.findOne(id))
                .map(o -> new ResponseEntity<>(genreResourceAssembler.toResource(o.getGenre()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/findByName",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<Artist>> getArtistsByNameLike(@RequestParam("name") String name, Pageable pageRequest) {
        PagedResources<Resource<Artist>> pagedResources = pagedAssembler.toResource(
                repository.findByNameIgnoreCaseContaining(name, pageRequest),
                assembler);
        return new ResponseEntity(pagedResources, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/findByCountry",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<Artist>> getArtistsByCountryLike(@RequestParam("country") String country, Pageable pageRequest) {
        PagedResources<Resource<Artist>> pagedResources = pagedAssembler.toResource(
                repository.findByCountryIgnoreCaseContaining(country, pageRequest),
                assembler);
        return new ResponseEntity(pagedResources, HttpStatus.OK);
    }


}
