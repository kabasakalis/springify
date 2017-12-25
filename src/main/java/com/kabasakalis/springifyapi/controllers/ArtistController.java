
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
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

    private AlbumRepository albumRepository;

    @Autowired
    @Qualifier("artistRepository")
    private ArtistRepository repository;
    private PagedResourcesAssembler<Album> pagedAlbumAssembler;
    private AlbumResourceAssembler albumResourceAssembler;
    private GenreResourceAssembler genreResourceAssembler;

    @Autowired
    public ArtistController(ArtistRepository repository,
                            AlbumResourceAssembler albumResourceAssembler,
                            AlbumRepository albumRepository,
                            GenreResourceAssembler genreResourceAssembler,
                            ArtistResourceAssembler assembler) {
        super(repository, assembler);
        this.albumRepository = albumRepository;
        this.albumResourceAssembler = albumResourceAssembler;
        this.genreResourceAssembler = genreResourceAssembler;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedAlbumAssembler = new PagedResourcesAssembler<Album>(resolver, null);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/albums",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<Album>> getArtistAlbums(
            Pageable pageRequest,
            @PathVariable Long id) {
        PagedResources<Resource<Album>>
                pagedArtistAlbums = pagedAlbumAssembler.toResource(
                albumRepository.findAllByArtistId(id, pageRequest),
                albumResourceAssembler);
        return new ResponseEntity(pagedArtistAlbums, HttpStatus.OK);
    }



        @RequestMapping(
            method = RequestMethod.POST,
            path = "/{id}/albums",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<? extends ResourceSupport> addArtistAlbum(
                @PathVariable long id,
               @RequestBody(required = false) Resources<Album> albumLinks  ) {
//       T entity= repository.save(entityBody);
        HttpHeaders httpHeaders = new HttpHeaders();
           List<Album> albums = new ArrayList<Album>();
            // Add to the existing collection
            for (Link link : albumLinks.getLinks()) {
                albums.add( (Album) loadEntity(albumRepository, link));

            }

            Artist artist = repository.findOne(id);
            artist.setAlbums(albums);
            repository.save(artist);
//        URI location_link = linkTo(methodOn(ArtistController.class).getOne(entity.getId())).toUri();
//        httpHeaders.setLocation(location_link);
//        return new ResponseEntity(albumResourceAssembler.toResource(entity), httpHeaders, HttpStatus.CREATED);
//            return new ResponseEntity( HttpStatus.OK);
//            return ControllerUtils.toEmptyResponse(HttpStatus);
            return ControllerUtils.toEmptyResponse(HttpStatus.NO_CONTENT);
    }


//	private Album loadAlbum(Class<?> type, Link link) {
//
//		String href = link.expand().getHref();
//		String id = href.substring(href.lastIndexOf('/') + 1);
//
//		RepositoryInvoker invoker = repositoryInvokerFactory.getInvokerFor(type);
//
//		return invoker.invokeFindOne(id);
//	}

//	private Object loadPropertyValue(Class<?> type, Link link) {
//
//		String href = link.expand().getHref();
//		String id = href.substring(href.lastIndexOf('/') + 1);
//
//		RepositoryInvoker invoker = repositoryInvokerFactory.getInvokerFor(type);
//
//		return invoker.invokeFindOne(id);
//	}





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
