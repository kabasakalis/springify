

package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.*;
import com.kabasakalis.springifyapi.models.*;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.PlaylistRepository;
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
@RequestMapping("/albums")
public class AlbumController extends AbstractBaseRestController<Album> {


    @Autowired
    private AlbumResourceAssembler assembler;
    private PlaylistRepository playlistRepository;
    private PlaylistResourceAssembler playlistResourceAssembler;
    private ArtistResourceAssembler artistResourceAssembler;
    private PagedResourcesAssembler<Playlist> pagedPlaylistAssembler;

    @Autowired
    public AlbumController(AlbumRepository repository,
                           PlaylistRepository playlistRepository,
                           PlaylistResourceAssembler playlistResourceAssembler,
                           ArtistResourceAssembler artistResourceAssembler,
                           ApplicationContext appContext,
                           AlbumResourceAssembler assembler) {

        super(repository, appContext, assembler);
        this.playlistRepository = playlistRepository;
        this.playlistResourceAssembler = playlistResourceAssembler;
        this.artistResourceAssembler = artistResourceAssembler;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedPlaylistAssembler = new PagedResourcesAssembler<Playlist>(resolver, null);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/playlists",
            consumes = {"application/json"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getAlbumPlaylists(
            Pageable pageRequest,
            @PathVariable Long id) {
        Page<Playlist> pagedPlaylistsByAlbum = playlistRepository.findAllByAlbums(repository.getOne(id), pageRequest);
        return getAssociatedResources(playlistResourceAssembler, pagedPlaylistAssembler, pagedPlaylistsByAlbum, pageRequest);
    }

    @RequestMapping(
            method = {RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.POST},
            path = "/{id}/playlists",
            consumes = {"application/json", "text/uri-list"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<? extends ResourceSupport> addArtistAlbums(
            @PathVariable long id,
            @RequestBody(required = false) Resources<? extends BaseEntity> playlistLinks) {
        return associateResources(Association.MANY_TO_MANY, playlistRepository, id, playlistLinks);
    }


    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{id}/playlists/{playlistId}",
            consumes = {"application/json"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity deleteAlbum(
            @PathVariable Long id, @PathVariable Long playlistId) {
        return deleteAssociation(Association.MANY_TO_MANY, playlistRepository, id, playlistId);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/artist",
            consumes = {"application/json"},
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> getArtistGenre(
            @PathVariable Long id) {
        return getAssociatedResource(id, Artist.class, artistResourceAssembler);
    }


}
