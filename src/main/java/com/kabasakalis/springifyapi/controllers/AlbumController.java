

package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.assemblers.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.assemblers.PagedCustomResourcesAssembler;
import com.kabasakalis.springifyapi.assemblers.PlaylistResourceAssembler;
import com.kabasakalis.springifyapi.domain.Album;
import com.kabasakalis.springifyapi.domain.Artist;
import com.kabasakalis.springifyapi.domain.BaseEntity;
import com.kabasakalis.springifyapi.domain.Playlist;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.PlaylistRepository;
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
@RequestMapping("/albums")
public class AlbumController extends AbstractBaseRestController<Album> {


    @Autowired
    private AlbumResourceAssembler assembler;
    private PlaylistRepository playlistRepository;
    private PlaylistResourceAssembler playlistResourceAssembler;
    private ArtistResourceAssembler artistResourceAssembler;
    private PagedCustomResourcesAssembler<Playlist> pagedPlaylistAssembler;

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
        this.pagedPlaylistAssembler = new PagedCustomResourcesAssembler<Playlist>(resolver, null);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/playlists",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getPlaylists(
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
    public ResponseEntity<? extends ResourceSupport> addPlaylistAssociations(
            @PathVariable long id,
            @RequestBody(required = false) Resources<? extends BaseEntity> playlistLinks) {
        return associateResources(Association.MANY_TO_MANY,Playlist.class, playlistRepository, id, playlistLinks);
    }


    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{id}/playlists/{playlistId}",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity deletePlaylistAssociation(
            @PathVariable Long id, @PathVariable Long playlistId) {
        return deleteAssociation(Association.MANY_TO_MANY,Playlist.class, playlistRepository, id, playlistId);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}/artist",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> getArtist(
            @PathVariable Long id) {
        return getAssociatedResource(id, Artist.class, artistResourceAssembler);
    }


}
