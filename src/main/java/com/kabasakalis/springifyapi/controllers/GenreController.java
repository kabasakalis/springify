

package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.assemblers.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.GenreResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.PagedCustomResourcesAssembler;
import com.kabasakalis.springifyapi.domain.Artist;
import com.kabasakalis.springifyapi.domain.BaseEntity;
import com.kabasakalis.springifyapi.domain.Genre;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RepositoryRestController
@RequestMapping("/genres")
public class GenreController extends AbstractBaseRestController<Genre> {


    private GenreResourceAssembler assembler;
    private PagedCustomResourcesAssembler<Artist> pagedArtistAssembler;
    private ArtistResourceAssembler artistResourceAssembler;
    private ArtistRepository artistRepository;


    @Autowired
    public GenreController(GenreRepository repository,
                           ArtistRepository artistRepository,
                           ArtistResourceAssembler artistResourceAssembler,
                           ApplicationContext appContext,
                           GenreResourceAssembler assembler) {
        super(repository,appContext, assembler);
        this.pagedArtistAssembler = pagedArtistAssembler;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedArtistAssembler = new PagedCustomResourcesAssembler<Artist>(resolver, null);
        this.artistRepository = artistRepository;
        this.assembler = assembler;
        this.artistResourceAssembler = artistResourceAssembler;
    }

        @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/artists",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> getArtists(
            Pageable pageRequest,
            @PathVariable Long id) {
            Page<Artist> pagedArtistsByGenreId = artistRepository.findAllByGenreId(id, pageRequest);
        return getAssociatedResources(artistResourceAssembler, pagedArtistAssembler, pagedArtistsByGenreId, pageRequest
        );
    }


    @RequestMapping(
            method = {RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.POST},
            path = "/{id}/artists",
            consumes = {"application/json", "text/uri-list"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<? extends ResourceSupport> addArtistAssociations(
            @PathVariable long id,
            @RequestBody(required = false) Resources<? extends BaseEntity> artistLinks) {
        return associateResources(Association.ONE_TO_MANY, Artist.class, artistRepository,id, artistLinks);
    }


    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{id}/artists/{artistId}",
            consumes = {"application/json"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity deleteArtistAssociation(
            @PathVariable Long id, @PathVariable Long artistId) {
        return deleteAssociation(Association.ONE_TO_MANY,  Artist.class,artistRepository, id, artistId);
    }

}
