
package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.GenreResourceAssembler;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.Genre;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;


@RepositoryRestController
@RequestMapping("/artists")
public class ArtistController extends AbstractBaseRestController<Artist> {

    private AlbumRepository albumRepository;
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
            method = RequestMethod.GET,
            path = "/{id}/genre",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<Genre>> getGenre(@PathVariable Long id) {
        return Optional.ofNullable(repository.findOne(id))
                .map(o -> new ResponseEntity<>(genreResourceAssembler.toResource(o.getGenre()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
